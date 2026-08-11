package com.abhimanyu.reservation;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CancellationFrame extends JFrame {

    private JTextField txtPNR;
    private JTextArea txtDetails;
    private JButton btnSearch;
    private JButton btnCancel;

    private Reservation currentReservation;

    private final ReservationDAO reservationDAO;
    private final PassengerDAO passengerDAO;
    private final SeatInventoryDAO seatInventoryDAO;

    public CancellationFrame() {

        reservationDAO = new ReservationDAO();
        passengerDAO = new PassengerDAO();
        seatInventoryDAO = new SeatInventoryDAO();

        setTitle("Ticket Cancellation");

        setSize(500, 400);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLayout(
                new BorderLayout(
                        10,
                        10
                )
        );

        // ==========================================
        // Top Panel
        // ==========================================

        JPanel topPanel =
                new JPanel(
                        new FlowLayout()
                );

        topPanel.add(
                new JLabel("PNR")
        );

        txtPNR =
                new JTextField(15);

        topPanel.add(txtPNR);

        btnSearch =
                new JButton("Search");

        topPanel.add(btnSearch);

        add(
                topPanel,
                BorderLayout.NORTH
        );

        // ==========================================
        // Details
        // ==========================================

        txtDetails =
                new JTextArea();

        txtDetails.setEditable(false);

        txtDetails.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        14
                )
        );

        add(
                new JScrollPane(txtDetails),
                BorderLayout.CENTER
        );

        // ==========================================
        // Bottom Panel
        // ==========================================

        JPanel bottomPanel =
                new JPanel();

        btnCancel =
                new JButton("Cancel Ticket");

        btnCancel.setEnabled(false);

        bottomPanel.add(btnCancel);

        add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        // ==========================================
        // Listeners
        // ==========================================

        btnSearch.addActionListener(
                e -> searchReservation()
        );

        btnCancel.addActionListener(
                e -> cancelReservation()
        );

        setVisible(true);
    }

    // ==========================================
    // Search Reservation
    // ==========================================

    private void searchReservation() {

        String pnr =
                txtPNR.getText().trim();

        if (pnr.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a PNR.",
                    "Input Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        currentReservation =
                reservationDAO
                        .getReservationByPNR(pnr);

        if (currentReservation == null) {

            txtDetails.setText("");

            btnCancel.setEnabled(false);

            JOptionPane.showMessageDialog(
                    this,
                    "Reservation not found.",
                    "Search Result",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Get passengers for this reservation

        List<Passenger> passengers =
                passengerDAO
                        .getPassengersByPNR(
                                currentReservation.getPnr()
                        );

        txtDetails.setText(
                "PNR           : "
                        + currentReservation.getPnr()
                        + "\n\n"

                        + "Passenger     : "
                        + currentReservation
                                .getPassengerName()
                        + "\n\n"

                        + "Train         : "
                        + currentReservation
                                .getTrainName()
                        + "\n\n"

                        + "Train Number  : "
                        + currentReservation
                                .getTrainNumber()
                        + "\n\n"

                        + "Class         : "
                        + currentReservation
                                .getClassType()
                        + "\n\n"

                        + "Passengers    : "
                        + passengers.size()
                        + "\n\n"

                        + "Journey Date  : "
                        + currentReservation
                                .getJourneyDate()
                        + "\n\n"

                        + "Source        : "
                        + currentReservation
                                .getSource()
                        + "\n\n"

                        + "Destination   : "
                        + currentReservation
                                .getDestination()
        );

        btnCancel.setEnabled(true);
    }

    // ==========================================
    // Cancel Reservation
    // ==========================================

    private void cancelReservation() {

    if (currentReservation == null) {
        JOptionPane.showMessageDialog(
                this,
                "Please search for a reservation first."
        );
        return;
    }

    int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to cancel this ticket?",
            "Confirm Cancellation",
            JOptionPane.YES_NO_OPTION
    );

    if (choice != JOptionPane.YES_OPTION) {
        return;
    }

    BookingService bookingService =
            new BookingService();

    boolean cancelled =
            bookingService.cancelReservation(
                    currentReservation.getPnr()
            );

    if (cancelled) {

        JOptionPane.showMessageDialog(
                this,
                "Reservation Cancelled Successfully!",
                "Cancellation Successful",
                JOptionPane.INFORMATION_MESSAGE
        );

        currentReservation = null;

        txtDetails.setText("");

        txtPNR.setText("");

        btnCancel.setEnabled(false);

    } else {

        JOptionPane.showMessageDialog(
                this,
                "Cancellation Failed!\n"
                        + "No changes were made to the database.",
                "Cancellation Error",
                JOptionPane.ERROR_MESSAGE
        );
    }


        // ==========================================
        // Get passengers
        // ==========================================

        List<Passenger> passengers =
                passengerDAO
                        .getPassengersByPNR(
                                currentReservation.getPnr()
                        );

        int passengerCount =
                passengers.size();

        if (passengerCount <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "No passengers found for this reservation.",
                    "Cancellation Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // ==========================================
        // Release Seats
        // ==========================================

        boolean seatsReleased =
                seatInventoryDAO.releaseSeats(
                        currentReservation.getTrainNumber(),
                        currentReservation.getClassType(),
                        passengerCount
                );

        if (!seatsReleased) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to release the seats.\n"
                            + "Cancellation has been stopped.",
                    "Cancellation Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // ==========================================
        // Delete Passengers
        // ==========================================

        boolean passengersDeleted =
                passengerDAO
                        .deletePassengersByPNR(
                                currentReservation.getPnr()
                        );

        if (!passengersDeleted) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to delete passenger records.\n"
                            + "Cancellation has been stopped.",
                    "Cancellation Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // ==========================================
        // Delete Reservation
        // ==========================================

        boolean reservationDeleted =
                reservationDAO
                        .deleteReservation(
                                currentReservation.getPnr()
                        );

        if (reservationDeleted) {

            JOptionPane.showMessageDialog(
                    this,
                    "Reservation Cancelled Successfully!\n\n"
                            + "PNR: "
                            + currentReservation.getPnr()
                            + "\n"
                            + "Released Seats: "
                            + passengerCount,
                    "Cancellation Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Reset screen

            txtDetails.setText("");

            txtPNR.setText("");

            btnCancel.setEnabled(false);

            currentReservation = null;

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Reservation deletion failed.",
                    "Cancellation Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}