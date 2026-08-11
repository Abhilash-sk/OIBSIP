package com.abhimanyu.reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticsDAO {

    public DashboardStats getDashboardStats() {

        String sql = """
                SELECT
                    (SELECT COUNT(*) FROM reservations) AS total_bookings,
                    (SELECT COUNT(*) FROM passengers) AS total_passengers,
                    COALESCE(
                        (SELECT SUM(total_fare) FROM reservations),
                        0
                    ) AS total_revenue
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            if (resultSet.next()) {

                return new DashboardStats(
                        resultSet.getInt("total_bookings"),
                        resultSet.getInt("total_passengers"),
                        resultSet.getDouble("total_revenue")
                );
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return new DashboardStats(0, 0, 0);
    }
}