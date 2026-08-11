package com.abhimanyu.reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public User authenticate(
            String username,
            String password
    ) {

        String sql = """
                SELECT user_id, username, password, role
                FROM users
                WHERE username = ?
                AND password = ?
                """;

        try (
                Connection con =
                        DatabaseConnection.getConnection();

                PreparedStatement pstmt =
                        con.prepareStatement(sql)
        ) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (
                    ResultSet rs =
                            pstmt.executeQuery()
            ) {

                if (rs.next()) {

                    User user = new User();

                    user.setUserId(
                            rs.getInt("user_id")
                    );

                    user.setUsername(
                            rs.getString("username")
                    );

                    user.setPassword(
                            rs.getString("password")
                    );

                    user.setRole(
                            rs.getString("role")
                    );

                    return user;
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }
}