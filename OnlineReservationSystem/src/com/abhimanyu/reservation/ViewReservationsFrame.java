package com.abhimanyu.reservation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ViewReservationsFrame extends JFrame {

    private final DefaultTableModel tableModel;
    private final JTable reservationTable;

    public ViewReservationsFrame() {

        setTitle("All Reservations");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(13, 71, 161));
        headerPanel.setBorder(new EmptyBorder(18, 20, 18, 20));

        JLabel titleLabel = new JLabel(
                "ALL RAILWAY RESERVATIONS",
                SwingConstants.CENTER
        );

        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 23));

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        String[] columns = {
                "PNR",
                "Passenger",
                "Train No.",
                "Train Name",
                "Class",
                "Total Fare",
                "Journey Date",
                "Source",
                "Destination"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        reservationTable = new JTable(tableModel);
        reservationTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        reservationTable.setRowHeight(30);
        reservationTable.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );

        reservationTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        reservationTable.setAutoCreateRowSorter(true);

        reservationTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent event) {

                if (event.getClickCount() == 2) {
                    openSelectedTicket();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(reservationTable);
        scrollPane.setBorder(new EmptyBorder(15, 15, 10, 15));

        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton refreshButton = createButton("Refresh");
        JButton openTicketButton = createButton("Open Ticket");
        JButton closeButton = createButton("Close");

        refreshButton.addActionListener(e -> loadReservations());
        openTicketButton.addActionListener(e -> openSelectedTicket());
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(refreshButton);
        buttonPanel.add(openTicketButton);
        buttonPanel.add(closeButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadReservations();

        setVisible(true);
    }

    private void loadReservations() {

        tableModel.setRowCount(0);

        ReservationDAO reservationDAO = new ReservationDAO();

        List<Reservation> reservations =
                reservationDAO.getAllReservations();

        for (Reservation reservation : reservations) {

            tableModel.addRow(new Object[]{
                    reservation.getPnr(),
                    reservation.getPassengerName(),
                    reservation.getTrainNumber(),
                    reservation.getTrainName(),
                    reservation.getClassType(),
                    String.format("₹%.2f", reservation.getTotalFare()),
                    reservation.getJourneyDate(),
                    reservation.getSource(),
                    reservation.getDestination()
            });
        }

        if (reservations.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No reservations are currently available.",
                    "Reservations",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void openSelectedTicket() {

        int selectedViewRow = reservationTable.getSelectedRow();

        if (selectedViewRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a reservation first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int selectedModelRow =
                reservationTable.convertRowIndexToModel(selectedViewRow);

        String pnr =
                tableModel.getValueAt(selectedModelRow, 0).toString();

        ReservationDAO reservationDAO = new ReservationDAO();

        Reservation reservation =
                reservationDAO.getReservationByPNR(pnr);

        if (reservation == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "The selected reservation no longer exists.",
                    "Reservation Not Found",
                    JOptionPane.ERROR_MESSAGE
            );

            loadReservations();
            return;
        }

        PassengerDAO passengerDAO = new PassengerDAO();

        List<Passenger> passengers =
                passengerDAO.getPassengersByPNR(pnr);

        new TicketFrame(reservation, passengers);
    }

    private JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setBackground(new Color(25, 118, 210));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 40));

        return button;
    }
}