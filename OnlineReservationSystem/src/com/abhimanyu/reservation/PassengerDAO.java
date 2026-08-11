package com.abhimanyu.reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassengerDAO {

    // Insert Passenger
    public boolean insertPassenger(Passenger passenger) {

        String sql = "INSERT INTO passengers (pnr, passenger_name, age, gender, coach, seat_number) VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, passenger.getPnr());
            pstmt.setString(2, passenger.getPassengerName());
            pstmt.setInt(3, passenger.getAge());
            pstmt.setString(4, passenger.getGender());
            pstmt.setString(5, passenger.getCoach());
            pstmt.setString(6, passenger.getSeatNumber());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Passenger> getPassengersByPNR(
        Connection con,
        String pnr
) throws SQLException {

    List<Passenger> passengers =
            new ArrayList<>();

    String sql =
            "SELECT * FROM passengers WHERE pnr = ?";

    try (PreparedStatement pstmt =
                 con.prepareStatement(sql)) {

        pstmt.setString(1, pnr);

        try (ResultSet rs =
                     pstmt.executeQuery()) {

            while (rs.next()) {

                Passenger passenger =
                        new Passenger();

                passenger.setPassengerId(
                        rs.getInt("passenger_id")
                );

                passenger.setPnr(
                        rs.getString("pnr")
                );

                passenger.setPassengerName(
                        rs.getString("passenger_name")
                );

                passenger.setAge(
                        rs.getInt("age")
                );

                passenger.setGender(
                        rs.getString("gender")
                );

                passenger.setCoach(
                        rs.getString("coach")
                );

                passenger.setSeatNumber(
                        rs.getString("seat_number")
                );

                passengers.add(passenger);
            }
        }
    }

    return passengers;
}
    public boolean deletePassengersByPNR(
        Connection con,
        String pnr
) {

    String sql =
            "DELETE FROM passengers WHERE pnr = ?";

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

    public boolean insertPassenger(
        Connection con,
        Passenger passenger
) {

    String sql = """
            INSERT INTO passengers
            (pnr, passenger_name, age, gender, coach, seat_number)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    try (
            PreparedStatement pstmt =
                    con.prepareStatement(sql)
    ) {

        pstmt.setString(
                1,
                passenger.getPnr()
        );

        pstmt.setString(
                2,
                passenger.getPassengerName()
        );

        pstmt.setInt(
                3,
                passenger.getAge()
        );

        pstmt.setString(
                4,
                passenger.getGender()
        );

        pstmt.setString(
                5,
                passenger.getCoach()
        );

        pstmt.setString(
                6,
                passenger.getSeatNumber()
        );

        return pstmt.executeUpdate() > 0;

    } catch (SQLException e) {

        e.printStackTrace();

        return false;
    }
}

    // Get All Passengers by PNR
    public List<Passenger> getPassengersByPNR(String pnr) {

        List<Passenger> passengers = new ArrayList<>();

        String sql = "SELECT * FROM passengers WHERE pnr = ?";

        try (
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, pnr);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Passenger passenger = new Passenger();

                passenger.setPassengerId(rs.getInt("passenger_id"));
                passenger.setPnr(rs.getString("pnr"));
                passenger.setPassengerName(rs.getString("passenger_name"));
                passenger.setAge(rs.getInt("age"));
                passenger.setGender(rs.getString("gender"));
                passenger.setCoach(rs.getString("coach"));
                passenger.setSeatNumber(rs.getString("seat_number"));

                passengers.add(passenger);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return passengers;
    }

    // Delete Passengers by PNR
    public boolean deletePassengersByPNR(String pnr) {

        String sql = "DELETE FROM passengers WHERE pnr = ?";

        try (
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, pnr);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}