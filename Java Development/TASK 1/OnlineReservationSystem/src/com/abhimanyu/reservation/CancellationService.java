package com.abhimanyu.reservation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CancellationService {

    private final ReservationDAO reservationDAO =
            new ReservationDAO();

    private final PassengerDAO passengerDAO =
            new PassengerDAO();

    private final SeatInventoryDAO seatInventoryDAO =
            new SeatInventoryDAO();

    public boolean cancelReservation(String pnr) {

        if (pnr == null || pnr.trim().isEmpty()) {
            return false;
        }

        Connection connection =
                DatabaseConnection.getConnection();

        if (connection == null) {
            return false;
        }

        try {

            connection.setAutoCommit(false);

            // ==========================================
            // 1. Find Reservation
            // ==========================================

            Reservation reservation =
                    reservationDAO.getReservationByPNR(pnr);

            if (reservation == null) {

                connection.rollback();

                return false;
            }

            // ==========================================
            // 2. Find Passengers
            // ==========================================

            List<Passenger> passengers =
                    passengerDAO.getPassengersByPNR(pnr);

            int passengerCount =
                    passengers.size();

            // ==========================================
            // 3. Release Seats
            // ==========================================

            boolean seatsReleased =
                    seatInventoryDAO.releaseSeats(
                            connection,
                            reservation.getTrainNumber(),
                            reservation.getClassType(),
                            passengerCount
                    );

            if (!seatsReleased) {

                connection.rollback();

                return false;
            }

            // ==========================================
            // 4. Delete Passengers
            // ==========================================

            boolean passengersDeleted =
                    passengerDAO.deletePassengersByPNR(
                            connection,
                            pnr
                    );

            if (!passengersDeleted && passengerCount > 0) {

                connection.rollback();

                return false;
            }

            // ==========================================
            // 5. Delete Reservation
            // ==========================================

            boolean reservationDeleted =
                    reservationDAO.deleteReservation(
                            connection,
                            pnr
                    );

            if (!reservationDeleted) {

                connection.rollback();

                return false;
            }

            // ==========================================
            // 6. COMMIT
            // ==========================================

            connection.commit();

            return true;

        } catch (SQLException e) {

            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }

            e.printStackTrace();

            return false;

        } finally {

            try {

                connection.setAutoCommit(true);
                connection.close();

            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }
}