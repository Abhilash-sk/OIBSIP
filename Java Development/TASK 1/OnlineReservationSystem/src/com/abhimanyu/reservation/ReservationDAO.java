package com.abhimanyu.reservation;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationDAO {

    // Insert Reservation
    public boolean insertReservation(Reservation reservation) {

        String sql = "INSERT INTO reservations (pnr, passenger_name, train_number, train_name, class_type, total_fare, journey_date, source, destination) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, reservation.getPnr());
            pstmt.setString(2, reservation.getPassengerName());
            pstmt.setInt(3, reservation.getTrainNumber());
            pstmt.setString(4, reservation.getTrainName());
            pstmt.setString(5, reservation.getClassType());
            pstmt.setDouble(6, reservation.getTotalFare());
            pstmt.setString(7, reservation.getJourneyDate());
            pstmt.setString(8, reservation.getSource());
            pstmt.setString(9, reservation.getDestination());

            int rowsInserted = pstmt.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteReservation(
        Connection con,
        String pnr
) {

    String sql =
            "DELETE FROM reservations WHERE pnr = ?";

    try (
            PreparedStatement pstmt =
                    con.prepareStatement(sql)
    ) {

        pstmt.setString(1, pnr);

        return pstmt.executeUpdate() > 0;

    } catch (SQLException e) {

        e.printStackTrace();

        return false;
    }
}
public Reservation getReservationByPNR(
        Connection con,
        String pnr
) throws SQLException {

    String sql =
            "SELECT * FROM reservations WHERE pnr = ?";

    try (PreparedStatement pstmt =
                 con.prepareStatement(sql)) {

        pstmt.setString(1, pnr);

        try (ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {

                Reservation reservation =
                        new Reservation();

                reservation.setPnr(
                        rs.getString("pnr")
                );

                reservation.setPassengerName(
                        rs.getString("passenger_name")
                );

                reservation.setTrainNumber(
                        rs.getInt("train_number")
                );

                reservation.setTrainName(
                        rs.getString("train_name")
                );

                reservation.setClassType(
                        rs.getString("class_type")
                );

                reservation.setTotalFare(
                        rs.getDouble("total_fare")
                );

                reservation.setJourneyDate(
                        rs.getString("journey_date")
                );

                reservation.setSource(
                        rs.getString("source")
                );

                reservation.setDestination(
                        rs.getString("destination")
                );

                return reservation;
            }
        }
    }

    return null;
}
    

    public boolean insertReservation(
        Connection con,
        Reservation reservation
) {

    String sql = """
            INSERT INTO reservations
            (pnr, passenger_name, train_number, train_name,
             class_type, total_fare, journey_date, source, destination)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    try (
            PreparedStatement pstmt =
                    con.prepareStatement(sql)
    ) {

        pstmt.setString(
                1,
                reservation.getPnr()
        );

        pstmt.setString(
                2,
                reservation.getPassengerName()
        );

        pstmt.setInt(
                3,
                reservation.getTrainNumber()
        );

        pstmt.setString(
                4,
                reservation.getTrainName()
        );

        pstmt.setString(
                5,
                reservation.getClassType()
        );

        pstmt.setDouble(
                6,
                reservation.getTotalFare()
        );

        pstmt.setString(
                7,
                reservation.getJourneyDate()
        );

        pstmt.setString(
                8,
                reservation.getSource()
        );

        pstmt.setString(
                9,
                reservation.getDestination()
        );

        return pstmt.executeUpdate() > 0;

    } catch (SQLException e) {

        e.printStackTrace();

        return false;
    }
}


    public List<Reservation> getAllReservations() {

    List<Reservation> reservations = new ArrayList<>();

    String sql = "SELECT * FROM reservations ORDER BY rowid DESC";

    try (
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()
    ) {

        while (rs.next()) {

            Reservation reservation = new Reservation();

            reservation.setPnr(rs.getString("pnr"));
            reservation.setPassengerName(rs.getString("passenger_name"));
            reservation.setTrainNumber(rs.getInt("train_number"));
            reservation.setTrainName(rs.getString("train_name"));
            reservation.setClassType(rs.getString("class_type"));
            reservation.setTotalFare(rs.getDouble("total_fare"));
            reservation.setJourneyDate(rs.getString("journey_date"));
            reservation.setSource(rs.getString("source"));
            reservation.setDestination(rs.getString("destination"));

            reservations.add(reservation);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return reservations;
}

    // Search Reservation by PNR
    public Reservation getReservationByPNR(String pnr) {

        String sql = "SELECT * FROM reservations WHERE pnr = ?";

        try (
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, pnr);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                Reservation reservation = new Reservation();

                reservation.setPnr(rs.getString("pnr"));
                reservation.setPassengerName(rs.getString("passenger_name"));
                reservation.setTrainNumber(rs.getInt("train_number"));
                reservation.setTrainName(rs.getString("train_name"));
                reservation.setClassType(rs.getString("class_type"));
                reservation.setTotalFare(rs.getDouble("total_fare"));
                reservation.setJourneyDate(rs.getString("journey_date"));
                reservation.setSource(rs.getString("source"));
                reservation.setDestination(rs.getString("destination"));

                return reservation;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    

    // Delete Reservation
    public boolean deleteReservation(String pnr) {

        String sql = "DELETE FROM reservations WHERE pnr = ?";

        try (
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, pnr);

            int rowsDeleted = pstmt.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
    }
}