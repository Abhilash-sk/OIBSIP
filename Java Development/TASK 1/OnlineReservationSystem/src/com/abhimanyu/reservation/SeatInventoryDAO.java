package com.abhimanyu.reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatInventoryDAO {

    // Get available seats
    public int getAvailableSeats(int trainNumber, String classType) {

        String sql = """
                SELECT available_seats
                FROM seat_inventory
                WHERE train_number = ?
                AND class_type = ?
                """;

        try (
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)
        ) {

            pstmt.setInt(1, trainNumber);
            pstmt.setString(2, classType);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("available_seats");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public boolean releaseSeats(
        Connection con,
        int trainNumber,
        String classType,
        int count
) {

    String sql = """
            UPDATE seat_inventory
            SET available_seats = available_seats + ?
            WHERE train_number = ?
            AND class_type = ?
            """;

    try (
            PreparedStatement pstmt =
                    con.prepareStatement(sql)
    ) {

        pstmt.setInt(1, count);
        pstmt.setInt(2, trainNumber);
        pstmt.setString(3, classType);

        return pstmt.executeUpdate() > 0;

    } catch (SQLException e) {

        e.printStackTrace();

        return false;
    }
}

     public int getAvailableSeats(
        Connection con,
        int trainNumber,
        String classType
) {

    String sql = """
            SELECT available_seats
            FROM seat_inventory
            WHERE train_number = ?
            AND class_type = ?
            """;

    try (
            PreparedStatement pstmt =
                    con.prepareStatement(sql)
    ) {

        pstmt.setInt(
                1,
                trainNumber
        );

        pstmt.setString(
                2,
                classType
        );

        try (
                ResultSet rs =
                        pstmt.executeQuery()
        ) {

            if (rs.next()) {

                return rs.getInt(
                        "available_seats"
                );
            }
        }

    } catch (SQLException e) {

        e.printStackTrace();
    }

    return 0;
}

public boolean reserveSeats(
        Connection con,
        int trainNumber,
        String classType,
        int count
) {

    String sql = """
            UPDATE seat_inventory
            SET available_seats = available_seats - ?
            WHERE train_number = ?
            AND class_type = ?
            AND available_seats >= ?
            """;

    try (
            PreparedStatement pstmt =
                    con.prepareStatement(sql)
    ) {

        pstmt.setInt(
                1,
                count
        );

        pstmt.setInt(
                2,
                trainNumber
        );

        pstmt.setString(
                3,
                classType
        );

        pstmt.setInt(
                4,
                count
        );

        return pstmt.executeUpdate() > 0;

    } catch (SQLException e) {

        e.printStackTrace();

        return false;
    }
}

    // Reduce seats after booking
    public boolean reserveSeats(int trainNumber,
                                String classType,
                                int count) {

        String sql = """
                UPDATE seat_inventory
                SET available_seats = available_seats - ?
                WHERE train_number = ?
                AND class_type = ?
                AND available_seats >= ?
                """;

        try (
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)
        ) {

            pstmt.setInt(1, count);
            pstmt.setInt(2, trainNumber);
            pstmt.setString(3, classType);
            pstmt.setInt(4, count);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;

        }

    }

    // Increase seats after cancellation
    public boolean releaseSeats(int trainNumber,
                                String classType,
                                int count) {

        String sql = """
                UPDATE seat_inventory
                SET available_seats = available_seats + ?
                WHERE train_number = ?
                AND class_type = ?
                """;

        try (
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)
        ) {

            pstmt.setInt(1, count);
            pstmt.setInt(2, trainNumber);
            pstmt.setString(3, classType);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;

        }

    }
    public List<Object[]> getAllSeatInventory() {

    List<Object[]> inventory = new ArrayList<>();

    String sql = """
            SELECT train_number, class_type, available_seats
            FROM seat_inventory
            ORDER BY train_number, class_type
            """;

    try (
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()
    ) {

        while (rs.next()) {

            inventory.add(
                    new Object[]{
                            rs.getInt("train_number"),
                            rs.getString("class_type"),
                            rs.getInt("available_seats")
                    }
            );
        }

    } catch (SQLException e) {

        e.printStackTrace();
    }

    return inventory;
}

}