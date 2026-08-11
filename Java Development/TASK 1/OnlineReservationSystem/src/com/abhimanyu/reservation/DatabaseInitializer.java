package com.abhimanyu.reservation;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

    // ==========================================
    // Initialize Seat Inventory
    // ==========================================

    public static void initializeSeatInventory() {

        try {

            Connection con =
                    DatabaseConnection.getConnection();

            Statement stmt =
                    con.createStatement();

            stmt.executeUpdate("""
                INSERT OR IGNORE INTO seat_inventory
                VALUES
                (12627,'Sleeper',72,72),
                (12627,'3AC',64,64),
                (12627,'2AC',48,48),
                (12627,'1AC',24,24),

                (12628,'Sleeper',72,72),
                (12628,'3AC',64,64),
                (12628,'2AC',48,48),
                (12628,'1AC',24,24),

                (17307,'Sleeper',72,72),
                (17307,'3AC',64,64),
                (17307,'2AC',48,48),
                (17307,'1AC',24,24),

                (16591,'Sleeper',72,72),
                (16591,'3AC',64,64),
                (16591,'2AC',48,48),
                (16591,'1AC',24,24)
            """);

            stmt.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    // ==========================================
    // Initialize Database
    // ==========================================

    public static void initializeDatabase() {

        try {

            Connection con =
                    DatabaseConnection.getConnection();

            Statement stmt =
                    con.createStatement();

            // ==========================================
            // Reservation Table
            // ==========================================

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS reservations(
                    pnr TEXT PRIMARY KEY,
                    passenger_name TEXT,
                    train_number INTEGER,
                    train_name TEXT,
                    class_type TEXT,
                    total_fare REAL,
                    journey_date TEXT,
                    source TEXT,
                    destination TEXT
                )
            """);

            // ==========================================
            // Passenger Table
            // ==========================================

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS passengers(
                    passenger_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    pnr TEXT,
                    passenger_name TEXT,
                    age INTEGER,
                    gender TEXT,
                    coach TEXT,
                    seat_number TEXT,
                    FOREIGN KEY(pnr)
                        REFERENCES reservations(pnr)
                )
            """);

            // ==========================================
            // Seat Inventory Table
            // ==========================================

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS seat_inventory(
                    train_number INTEGER,
                    class_type TEXT,
                    total_seats INTEGER,
                    available_seats INTEGER,
                    PRIMARY KEY(train_number, class_type)
                )
            """);

            // ==========================================
            // Users Table
            // ==========================================

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users(
                    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL,
                    role TEXT NOT NULL
                )
            """);

            // ==========================================
            // Default USER Account
            // ==========================================

            stmt.executeUpdate("""
                INSERT OR IGNORE INTO users
                (username, password, role)
                VALUES
                ('abhimanyu', 'abhimanyu123', 'USER')
            """);

            // ==========================================
            // Default ADMIN Account
            // ==========================================

            stmt.executeUpdate("""
                INSERT OR IGNORE INTO users
                (username, password, role)
                VALUES
                ('admin', 'admin123', 'ADMIN')
            """);

            stmt.close();
            con.close();

            System.out.println(
                    "Database initialized successfully."
            );

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}