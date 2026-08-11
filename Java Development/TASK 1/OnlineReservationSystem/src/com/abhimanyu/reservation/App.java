package com.abhimanyu.reservation;

import javax.swing.SwingUtilities;

public class App {

    public static void main(String[] args) {

       DatabaseInitializer.initializeDatabase();

       DatabaseInitializer.initializeSeatInventory();

        SwingUtilities.invokeLater(() -> new LoginFrame());

    }

}