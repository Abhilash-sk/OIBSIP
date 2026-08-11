package com.abhimanyu.reservation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.print.PrinterException;
import java.util.List;

public class TicketFrame extends JFrame {

    private final JTextArea ticketArea;

    private static final Color PRIMARY_COLOR =
            new Color(13, 71, 161);

    private static final Color SECONDARY_COLOR =
            new Color(25, 118, 210);

   
    private static final Color LIGHT_GRAY =
            new Color(245, 247, 250);

    public TicketFrame(
            Reservation reservation,
            List<Passenger> passengers
    ) {

        setTitle("Indian Railways E-Ticket");

        setSize(850, 700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        getContentPane().setBackground(Color.WHITE);

        // ==================================================
        // HEADER
        // ==================================================

        JPanel headerPanel =
                new JPanel(new BorderLayout());

        headerPanel.setBackground(PRIMARY_COLOR);

        headerPanel.setBorder(
                new EmptyBorder(
                        18,
                        20,
                        18,
                        20
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "INDIAN RAILWAYS E-TICKET",
                        SwingConstants.CENTER
                );

        titleLabel.setForeground(Color.WHITE);

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        headerPanel.add(
                titleLabel,
                BorderLayout.CENTER
        );

        add(
                headerPanel,
                BorderLayout.NORTH
        );

        // ==================================================
        // TICKET AREA
        // ==================================================

        ticketArea = new JTextArea();

        ticketArea.setEditable(false);

        ticketArea.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        14
                )
        );

        ticketArea.setBackground(Color.WHITE);

        ticketArea.setForeground(
                new Color(40, 40, 40)
        );

        ticketArea.setMargin(
                new Insets(
                        20,
                        20,
                        20,
                        20
                )
        );

        StringBuilder ticket =
                new StringBuilder();

        ticket.append(
                "============================================================\n"
        );

        ticket.append(
                "                    TICKET DETAILS\n"
        );

        ticket.append(
                "============================================================\n\n"
        );

        ticket.append(
                "PNR              : "
        )
                .append(reservation.getPnr())
                .append("\n");

        ticket.append(
                "Train Number     : "
        )
                .append(reservation.getTrainNumber())
                .append("\n");

        ticket.append(
                "Train Name       : "
        )
                .append(reservation.getTrainName())
                .append("\n");

        ticket.append(
                "Class            : "
        )
                .append(reservation.getClassType())
                .append("\n");

        ticket.append(
                "Journey Date     : "
        )
                .append(reservation.getJourneyDate())
                .append("\n");

        ticket.append(
                "Source           : "
        )
                .append(reservation.getSource())
                .append("\n");

        ticket.append(
                "Destination      : "
        )
                .append(reservation.getDestination())
                .append("\n");

        ticket.append(
                "Total Passengers : "
        )
                .append(passengers.size())
                .append("\n");

        ticket.append(
                "Total Fare       : INR "
        )
                .append(
                        String.format(
                                "%.2f",
                                reservation.getTotalFare()
                        )
                )
                .append("\n");

        ticket.append(
                "Booking Status   : CONFIRMED"
        )
                .append("\n\n");

        ticket.append(
                "------------------------------------------------------------\n"
        );

        ticket.append(
                "                    PASSENGERS\n"
        );

        ticket.append(
                "------------------------------------------------------------\n\n"
        );

        int passengerNumber = 1;

        for (Passenger passenger : passengers) {

            ticket.append(
                    "Passenger "
            )
                    .append(passengerNumber++)
                    .append("\n");

            ticket.append(
                    "Name      : "
            )
                    .append(passenger.getPassengerName())
                    .append("\n");

            ticket.append(
                    "Age       : "
            )
                    .append(passenger.getAge())
                    .append("\n");

            ticket.append(
                    "Gender    : "
            )
                    .append(passenger.getGender())
                    .append("\n");

            ticket.append(
                    "Coach     : "
            )
                    .append(passenger.getCoach())
                    .append("\n");

            ticket.append(
                    "Seat      : "
            )
                    .append(passenger.getSeatNumber())
                    .append("\n\n");

            ticket.append(
                    "------------------------------------------------------------\n"
            );
        }

        ticket.append(
                "\n              THANK YOU FOR CHOOSING US!\n"
        );

        ticketArea.setText(
                ticket.toString()
        );

        JScrollPane scrollPane =
                new JScrollPane(ticketArea);

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        add(
                scrollPane,
                BorderLayout.CENTER
        );

        // ==================================================
        // QR CODE
        // ==================================================

        String qrData =
                "PNR: " +
                reservation.getPnr() +
                "\n" +
                "Train: " +
                reservation.getTrainName() +
                "\n" +
                "Train No: " +
                reservation.getTrainNumber() +
                "\n" +
                "Journey: " +
                reservation.getSource() +
                " -> " +
                reservation.getDestination() +
                "\n" +
                "Date: " +
                reservation.getJourneyDate() +
                "\n" +
                "Class: " +
                reservation.getClassType() +
                "\n" +
                "Fare: INR " +
                reservation.getTotalFare();

        ImageIcon qrIcon =
                QRCodeGenerator.generateQRCode(
                        qrData,
                        180,
                        180
                );

        JPanel qrPanel =
                new JPanel(
                        new BorderLayout()
                );

        qrPanel.setBackground(
                LIGHT_GRAY
        );

        qrPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                SECONDARY_COLOR,
                                2
                        ),
                        new EmptyBorder(
                                10,
                                10,
                                10,
                                10
                        )
                )
        );

        JLabel qrTitle =
                new JLabel(
                        "TICKET QR CODE",
                        SwingConstants.CENTER
                );

        qrTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        qrTitle.setForeground(
                PRIMARY_COLOR
        );

        qrPanel.add(
                qrTitle,
                BorderLayout.NORTH
        );

        JLabel qrLabel =
                new JLabel(
                        qrIcon,
                        SwingConstants.CENTER
                );

        qrPanel.add(
                qrLabel,
                BorderLayout.CENTER
        );

        add(
                qrPanel,
                BorderLayout.EAST
        );

        // ==================================================
        // BUTTON PANEL
        // ==================================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                15,
                                10
                        )
                );

        buttonPanel.setBackground(
                Color.WHITE
        );

        JButton printButton =
                new JButton(
                        "Print Ticket"
                );

        JButton pdfButton =
                new JButton(
                        "Save as PDF"
                );

        JButton closeButton =
                new JButton(
                        "Close"
                );

        styleButton(
                printButton
        );

        styleButton(
                pdfButton
        );

        styleCloseButton(
                closeButton
        );

        buttonPanel.add(
                printButton
        );

        buttonPanel.add(
                pdfButton
        );

        buttonPanel.add(
                closeButton
        );

        add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // ==================================================
        // BUTTON ACTIONS
        // ==================================================

        printButton.addActionListener(
                event -> printTicket()
        );

        pdfButton.addActionListener(
                event ->
                        PdfTicketExporter.exportTicket(
                                reservation,
                                passengers,
                                this
                        )
        );

        closeButton.addActionListener(
                event -> dispose()
        );

        setVisible(true);
    }

    // ==================================================
    // BUTTON STYLE
    // ==================================================

    private void styleButton(
            JButton button
    ) {

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        button.setBackground(
                SECONDARY_COLOR
        );

        button.setForeground(
                Color.WHITE
        );

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setOpaque(true);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }

    private void styleCloseButton(
            JButton button
    ) {

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        button.setBackground(
                new Color(
                        90,
                        90,
                        90
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setOpaque(true);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }

    // ==================================================
    // PRINT
    // ==================================================

    private void printTicket() {

        try {

            boolean complete =
                    ticketArea.print();

            if (complete) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ticket printed successfully!",
                        "Print Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Printing was cancelled.",
                        "Print Cancelled",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (PrinterException exception) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to print the ticket.\n\n"
                            + exception.getMessage(),
                    "Printing Error",
                    JOptionPane.ERROR_MESSAGE
            );

            exception.printStackTrace();
        }
    }
}