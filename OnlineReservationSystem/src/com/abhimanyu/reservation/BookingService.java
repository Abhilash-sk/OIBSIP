package com.abhimanyu.reservation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BookingService {

    private final ReservationDAO reservationDAO = new ReservationDAO();
    private final PassengerDAO passengerDAO = new PassengerDAO();
    private final SeatInventoryDAO seatInventoryDAO = new SeatInventoryDAO();

    public boolean bookReservation(
            Reservation reservation,
            List<Passenger> passengers
    ) {

        int passengerCount = passengers.size();

        try (Connection connection = DatabaseConnection.getConnection()) {

            if (connection == null) {
                return false;
            }

            connection.setAutoCommit(false);

            try {

                int availableSeats =
                        seatInventoryDAO.getAvailableSeats(
                                connection,
                                reservation.getTrainNumber(),
                                reservation.getClassType()
                        );

                if (availableSeats < passengerCount) {
                    connection.rollback();
                    return false;
                }

                if (!reservationDAO.insertReservation(
                        connection,
                        reservation
                )) {
                    connection.rollback();
                    return false;
                }

                for (Passenger passenger : passengers) {

                    if (!passengerDAO.insertPassenger(
                            connection,
                            passenger
                    )) {
                        connection.rollback();
                        return false;
                    }
                }

                if (!seatInventoryDAO.reserveSeats(
                        connection,
                        reservation.getTrainNumber(),
                        reservation.getClassType(),
                        passengerCount
                )) {
                    connection.rollback();
                    return false;
                }

                connection.commit();

                return true;

            } catch (SQLException exception) {

                connection.rollback();

                exception.printStackTrace();

                return false;

            } finally {

                connection.setAutoCommit(true);
            }

        } catch (SQLException exception) {

            exception.printStackTrace();

            return false;
        }
    }

    // ==========================================
    // Cancellation
    // ==========================================

    public boolean cancelReservation(String pnr) {

        try (Connection connection = DatabaseConnection.getConnection()) {

            if (connection == null) {
                return false;
            }

            connection.setAutoCommit(false);

            try {

                Reservation reservation =
                        reservationDAO.getReservationByPNR(
                                connection,
                                pnr
                        );

                if (reservation == null) {

                    connection.rollback();

                    return false;
                }

                List<Passenger> passengers =
                        passengerDAO.getPassengersByPNR(
                                connection,
                                pnr
                        );

                int passengerCount =
                        passengers.size();

                if (!passengerDAO.deletePassengersByPNR(
                        connection,
                        pnr
                )) {

                    connection.rollback();

                    return false;
                }

                if (!reservationDAO.deleteReservation(
                        connection,
                        pnr
                )) {

                    connection.rollback();

                    return false;
                }

                if (!seatInventoryDAO.releaseSeats(
                        connection,
                        reservation.getTrainNumber(),
                        reservation.getClassType(),
                        passengerCount
                )) {

                    connection.rollback();

                    return false;
                }

                connection.commit();

                return true;

            } catch (SQLException exception) {

                connection.rollback();

                exception.printStackTrace();

                return false;

            } finally {

                connection.setAutoCommit(true);
            }

        } catch (SQLException exception) {

            exception.printStackTrace();

            return false;
        }
    }
}