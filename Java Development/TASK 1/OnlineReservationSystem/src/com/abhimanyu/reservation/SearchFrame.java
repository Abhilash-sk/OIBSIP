package com.abhimanyu.reservation;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SearchFrame extends JFrame {

    private JTextField txtPNR;
    private JButton btnSearch;

    public SearchFrame() {

        setTitle("Search Reservation");

        setSize(450, 250);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        // ==========================================
        // Search Panel
        // ==========================================

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                3,
                                1,
                                10,
                                10
                        )
                );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        30,
                        20,
                        30
                )
        );

        JLabel lblTitle =
                new JLabel(
                        "Search Reservation by PNR",
                        SwingConstants.CENTER
                );

        lblTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        txtPNR = new JTextField();

        txtPNR.setHorizontalAlignment(
                JTextField.CENTER
        );

        btnSearch =
                new JButton("Search Ticket");

        btnSearch.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        panel.add(lblTitle);

        panel.add(txtPNR);

        panel.add(btnSearch);

        add(
                panel,
                BorderLayout.CENTER
        );

        // ==========================================
        // Search Button
        // ==========================================

        btnSearch.addActionListener(
                e -> searchReservation()
        );

        // ==========================================
        // Enter Key Support
        // ==========================================

        txtPNR.addActionListener(
                e -> searchReservation()
        );

        setVisible(true);
    }

    // ==========================================
    // Search Reservation
    // ==========================================

    private void searchReservation() {

        String pnr =
                txtPNR.getText()
                        .trim();

        // ==========================================
        // Validation
        // ==========================================

        if (pnr.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a PNR.",
                    "Invalid PNR",
                    JOptionPane.WARNING_MESSAGE
            );

            txtPNR.requestFocus();

            return;
        }

        // ==========================================
        // Search Reservation
        // ==========================================

        ReservationDAO reservationDAO =
                new ReservationDAO();

        Reservation reservation =
                reservationDAO.getReservationByPNR(
                        pnr
                );

        // ==========================================
        // PNR Not Found
        // ==========================================

        if (reservation == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "No reservation found for PNR:\n\n"
                            + pnr,
                    "PNR Not Found",
                    JOptionPane.ERROR_MESSAGE
            );

            txtPNR.selectAll();

            txtPNR.requestFocus();

            return;
        }

        // ==========================================
        // Get Passengers
        // ==========================================

        PassengerDAO passengerDAO =
                new PassengerDAO();

        List<Passenger> passengers =
                passengerDAO.getPassengersByPNR(
                        pnr
                );

        // ==========================================
        // Validate Passenger Data
        // ==========================================

        if (passengers.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Reservation found, but no passenger "
                            + "details are available.",
                    "Passenger Data Missing",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // ==========================================
        // Open Complete Ticket
        // ==========================================

        new TicketFrame(
                reservation,
                passengers
        );

        dispose();
    }
}