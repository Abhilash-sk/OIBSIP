package com.abhimanyu.reservation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BookingHistoryFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    private ReservationDAO reservationDAO;
    private PassengerDAO passengerDAO;

    public BookingHistoryFrame() {

        reservationDAO = new ReservationDAO();
        passengerDAO = new PassengerDAO();

        setTitle("Booking History");

        setSize(1100, 550);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        // ==========================================
        // Table Model
        // ==========================================

        model = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        model.setColumnIdentifiers(new String[]{
                "PNR",
                "Passenger",
                "Passengers",
                "Train No",
                "Train Name",
                "Class",
                "Journey Date",
                "Source",
                "Destination",
                "Fare"
        });

        // ==========================================
        // Table
        // ==========================================

        table = new JTable(model);

        table.setRowHeight(28);

        table.setAutoCreateRowSorter(true);

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        table.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        table.getTableHeader().setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        // ==========================================
        // Scroll Pane
        // ==========================================

        JScrollPane scrollPane =
                new JScrollPane(table);

        add(
                scrollPane,
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
                                10
                        )
                );

        JButton btnView =
                new JButton("View Ticket");

        JButton btnRefresh =
                new JButton("Refresh");

        JButton btnClose =
                new JButton("Close");

        buttonPanel.add(btnView);

        buttonPanel.add(btnRefresh);

        buttonPanel.add(btnClose);

        add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // ==========================================
        // Load Initial Data
        // ==========================================

        loadReservations();

        // ==========================================
        // Button Actions
        // ==========================================

        btnRefresh.addActionListener(
                e -> loadReservations()
        );

        btnView.addActionListener(
                e -> viewTicket()
        );

        btnClose.addActionListener(
                e -> dispose()
        );

        // ==========================================
        // Double Click Ticket
        // ==========================================

        table.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent event
                    ) {

                        if (event.getClickCount() == 2
                                && SwingUtilities.isLeftMouseButton(event)) {

                            viewTicket();
                        }
                    }
                }
        );

        setVisible(true);
    }

    // ==========================================
    // Load Reservations
    // ==========================================

    private void loadReservations() {

        model.setRowCount(0);

        List<Reservation> reservations =
                reservationDAO.getAllReservations();

        for (Reservation reservation : reservations) {

            List<Passenger> passengers =
                    passengerDAO.getPassengersByPNR(
                            reservation.getPnr()
                    );

            int passengerCount =
                    passengers.size();

            model.addRow(
                    new Object[]{

                            reservation.getPnr(),

                            reservation.getPassengerName(),

                            passengerCount,

                            reservation.getTrainNumber(),

                            reservation.getTrainName(),

                            reservation.getClassType(),

                            reservation.getJourneyDate(),

                            reservation.getSource(),

                            reservation.getDestination(),

                            String.format(
                                    "₹%.2f",
                                    reservation.getTotalFare()
                            )
                    }
            );
        }
    }

    // ==========================================
    // View Ticket
    // ==========================================

    private void viewTicket() {

        int selectedRow =
                table.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a reservation.",
                    "No Reservation Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Convert sorted table row to model row
        int modelRow =
                table.convertRowIndexToModel(
                        selectedRow
                );

        String pnr =
                model.getValueAt(
                        modelRow,
                        0
                ).toString();

        // ==========================================
        // Get Reservation
        // ==========================================

        Reservation reservation =
                reservationDAO.getReservationByPNR(
                        pnr
                );

        if (reservation == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Reservation not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // ==========================================
        // Get Passengers
        // ==========================================

        List<Passenger> passengers =
                passengerDAO.getPassengersByPNR(
                        pnr
                );

        if (passengers.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No passenger details found.",
                    "Passenger Data Missing",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // ==========================================
        // Open Ticket
        // ==========================================

        new TicketFrame(
                reservation,
                passengers
        );
    }
}