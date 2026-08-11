package com.abhimanyu.reservation;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminDashboardFrame extends JFrame {
        public static void main(String[] args) {

    SwingUtilities.invokeLater(
            AdminDashboardFrame::new
    );
}

    private JLabel totalBookingsLabel;
    private JLabel totalPassengersLabel;
    private JLabel totalRevenueLabel;

    private ReservationDAO reservationDAO;
    private PassengerDAO passengerDAO;

    public AdminDashboardFrame() {

        reservationDAO = new ReservationDAO();
        passengerDAO = new PassengerDAO();

        setTitle("IRON RAIL - Admin Dashboard");

        setSize(750, 500);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(15, 15));

        // ==========================================
        // Header
        // ==========================================

        JLabel titleLabel =
                new JLabel(
                        "IRON RAIL - ADMIN DASHBOARD",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        26
                )
        );

        titleLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        10,
                        20,
                        10
                )
        );

        add(titleLabel, BorderLayout.NORTH);

        // ==========================================
        // Statistics Panel
        // ==========================================

        JPanel statisticsPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                15,
                                15
                        )
                );

        statisticsPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        // Total Bookings

        totalBookingsLabel =
                createStatisticCard(
                        statisticsPanel,
                        "Total Bookings"
                );

        // Total Passengers

        totalPassengersLabel =
                createStatisticCard(
                        statisticsPanel,
                        "Total Passengers"
                );

        // Total Revenue

        totalRevenueLabel =
                createStatisticCard(
                        statisticsPanel,
                        "Total Revenue"
                );

        add(
                statisticsPanel,
                BorderLayout.CENTER
        );

        // ==========================================
        // Buttons
        // ==========================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                15,
                                15
                        )
                );

        JButton refreshButton =
                new JButton("Refresh");

        JButton bookingsButton =
                new JButton("View All Bookings");
                JButton inventoryButton =
        new JButton("Seat Inventory");

        JButton closeButton =
                new JButton("Close");

        refreshButton.addActionListener(
                e -> loadStatistics()
        );

        bookingsButton.addActionListener(
                e -> openBookingHistory()
        );
        inventoryButton.addActionListener(
        e -> new SeatInventoryFrame()
);

        closeButton.addActionListener(
                e -> dispose()
        );buttonPanel.add(refreshButton);

buttonPanel.add(bookingsButton);

buttonPanel.add(inventoryButton);

buttonPanel.add(closeButton);

        add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // Load initial statistics

        loadStatistics();

        setVisible(true);
    }

    // ==========================================
    // Create Statistic Card
    // ==========================================

    private JLabel createStatisticCard(
            JPanel parent,
            String title
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                Color.GRAY,
                                1
                        ),
                        BorderFactory.createEmptyBorder(
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

        JLabel valueLabel =
                new JLabel(
                        "0",
                        SwingConstants.CENTER
                );

        valueLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                valueLabel,
                BorderLayout.CENTER
        );

        parent.add(card);

        return valueLabel;
    }

    // ==========================================
    // Load Statistics
    // ==========================================

    private void loadStatistics() {

        List<Reservation> reservations =
                reservationDAO.getAllReservations();

        int totalBookings =
                reservations.size();

        int totalPassengers = 0;

        double totalRevenue = 0;

        for (Reservation reservation : reservations) {

            totalRevenue +=
                    reservation.getTotalFare();

            List<Passenger> passengers =
                    passengerDAO.getPassengersByPNR(
                            reservation.getPnr()
                    );

            totalPassengers +=
                    passengers.size();
        }

        totalBookingsLabel.setText(
                String.valueOf(totalBookings)
        );

        totalPassengersLabel.setText(
                String.valueOf(totalPassengers)
        );

        totalRevenueLabel.setText(
                String.format(
                        "₹%.2f",
                        totalRevenue
                )
        );
    }

    // ==========================================
    // Open Booking History
    // ==========================================

    private void openBookingHistory() {

        new BookingHistoryFrame();

    }
}