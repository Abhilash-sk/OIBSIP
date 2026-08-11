package com.abhimanyu.reservation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DashboardFrame extends JFrame {

    private JLabel bookingsValueLabel;
    private JLabel passengersValueLabel;
    private JLabel revenueValueLabel;

    public DashboardFrame() {

        setTitle("Indian Railways Reservation System");
        setSize(750, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(Color.WHITE);

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createNavigationPanel(), BorderLayout.CENTER);
        add(createStatisticsPanel(), BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowActivated(WindowEvent event) {
                loadDashboardStatistics();
            }
        });

        loadDashboardStatistics();

        setVisible(true);
    }

    // ==========================================
    // Header
    // ==========================================

    private JPanel createHeaderPanel() {

        JPanel headerPanel =
                new JPanel(new BorderLayout());

        headerPanel.setBackground(
                new Color(13, 71, 161)
        );

        headerPanel.setBorder(
                new EmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "INDIAN RAILWAYS RESERVATION SYSTEM",
                        SwingConstants.CENTER
                );

        titleLabel.setForeground(Color.WHITE);

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        25
                )
        );

        headerPanel.add(
                titleLabel,
                BorderLayout.CENTER
        );

        return headerPanel;
    }

    // ==========================================
    // Navigation
    // ==========================================

    private JPanel createNavigationPanel() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                15,
                                20
                        )
                );

        mainPanel.setBackground(Color.WHITE);

        mainPanel.setBorder(
                new EmptyBorder(
                        25,
                        50,
                        25,
                        50
                )
        );

        JLabel welcomeLabel =
                new JLabel(
                        "Welcome to your Dashboard",
                        SwingConstants.CENTER
                );

        welcomeLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );

        mainPanel.add(
                welcomeLabel,
                BorderLayout.NORTH
        );

        /*
         * 6 user buttons
         *
         * 3 rows x 2 columns
         */

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(
                                3,
                                2,
                                15,
                                15
                        )
                );

        buttonPanel.setBackground(Color.WHITE);

        JButton bookButton =
                createButton("Book Ticket");

        JButton searchButton =
                createButton("Search Ticket");

        JButton cancelButton =
                createButton("Cancel Ticket");

        JButton viewButton =
                createButton("View Reservations");

        JButton refreshButton =
                createButton("Refresh Statistics");

        JButton logoutButton =
                createButton("Logout");

        // ==========================================
        // Book
        // ==========================================

        bookButton.addActionListener(event -> {
            new ReservationFrame();
        });

        // ==========================================
        // Search
        // ==========================================

        searchButton.addActionListener(event -> {
            new SearchFrame();
        });

        // ==========================================
        // Cancel
        // ==========================================

        cancelButton.addActionListener(event -> {
            new CancellationFrame();
        });

        // ==========================================
        // View Reservations
        // ==========================================

        viewButton.addActionListener(event -> {
            new ViewReservationsFrame();
        });

        // ==========================================
        // Refresh
        // ==========================================

        refreshButton.addActionListener(event -> {
            loadDashboardStatistics();
        });

        // ==========================================
        // Logout
        // ==========================================

        logoutButton.addActionListener(event -> {

            new LoginFrame();

            dispose();
        });

        // ==========================================
        // Add Buttons
        // ==========================================

        buttonPanel.add(bookButton);
        buttonPanel.add(searchButton);

        buttonPanel.add(cancelButton);
        buttonPanel.add(viewButton);

        buttonPanel.add(refreshButton);
        buttonPanel.add(logoutButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.CENTER
        );

        return mainPanel;
    }

    // ==========================================
    // Statistics
    // ==========================================

    private JPanel createStatisticsPanel() {

        JPanel statisticsPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                15,
                                15
                        )
                );

        statisticsPanel.setBackground(
                Color.WHITE
        );

        statisticsPanel.setBorder(
                new EmptyBorder(
                        10,
                        25,
                        25,
                        25
                )
        );

        bookingsValueLabel =
                createValueLabel();

        passengersValueLabel =
                createValueLabel();

        revenueValueLabel =
                createValueLabel();

        statisticsPanel.add(
                createStatisticCard(
                        "Total Bookings",
                        bookingsValueLabel
                )
        );

        statisticsPanel.add(
                createStatisticCard(
                        "Total Passengers",
                        passengersValueLabel
                )
        );

        statisticsPanel.add(
                createStatisticCard(
                        "Total Revenue",
                        revenueValueLabel
                )
        );

        return statisticsPanel;
    }

    // ==========================================
    // Statistic Card
    // ==========================================

    private JPanel createStatisticCard(
            String title,
            JLabel valueLabel
    ) {

        JPanel cardPanel =
                new JPanel(
                        new BorderLayout(
                                5,
                                5
                        )
                );

        cardPanel.setBackground(
                new Color(
                        245,
                        247,
                        250
                )
        );

        cardPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        210,
                                        210,
                                        210
                                )
                        ),
                        new EmptyBorder(
                                15,
                                10,
                                15,
                                10
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(
                        title,
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        cardPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        cardPanel.add(
                valueLabel,
                BorderLayout.CENTER
        );

        return cardPanel;
    }

    // ==========================================
    // Value Label
    // ==========================================

    private JLabel createValueLabel() {

        JLabel label =
                new JLabel(
                        "0",
                        SwingConstants.CENTER
                );

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        25
                )
        );

        label.setForeground(
                new Color(
                        13,
                        71,
                        161
                )
        );

        return label;
    }

    // ==========================================
    // Button
    // ==========================================

    private JButton createButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        button.setBackground(
                new Color(
                        25,
                        118,
                        210
                )
        );

        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    // ==========================================
    // Dashboard Statistics
    // ==========================================

    private void loadDashboardStatistics() {

        StatisticsDAO statisticsDAO =
                new StatisticsDAO();

        DashboardStats statistics =
                statisticsDAO.getDashboardStats();

        bookingsValueLabel.setText(
                String.valueOf(
                        statistics.totalBookings()
                )
        );

        passengersValueLabel.setText(
                String.valueOf(
                        statistics.totalPassengers()
                )
        );

        revenueValueLabel.setText(
                String.format(
                        "₹%.2f",
                        statistics.totalRevenue()
                )
        );
    }
}