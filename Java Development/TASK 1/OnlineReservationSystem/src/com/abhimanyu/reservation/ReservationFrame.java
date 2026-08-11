package com.abhimanyu.reservation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ReservationFrame extends JFrame {

    // ==========================================
    // Passenger fields
    // ==========================================

    private final List<JTextField> passengerNameFields =
            new ArrayList<>();

    private final List<JTextField> passengerAgeFields =
            new ArrayList<>();

    private final List<JComboBox<String>> passengerGenderFields =
            new ArrayList<>();

    // ==========================================
    // Main fields
    // ==========================================

    private JTextField txtPassengerName;
    private JTextField txtTrainNumber;
    private JTextField txtTrainName;
    private JTextField txtFare;
    private JTextField txtJourneyDate;
    private JTextField txtSource;
    private JTextField txtDestination;

    private JComboBox<String> cmbClassType;
    private JComboBox<Integer> cmbPassengerCount;

    private JPanel passengerPanel;

    private JButton btnBook;

    // ==========================================
    // Constructor
    // ==========================================

    public ReservationFrame() {

        setTitle("Online Reservation System");

        setSize(650, 800);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        // ==========================================
        // Main Form
        // ==========================================

        JPanel formPanel = new JPanel();

        formPanel.setLayout(
                new GridLayout(
                        9,
                        2,
                        10,
                        10
                )
        );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        // ==========================================
        // Passenger Count
        // ==========================================

        formPanel.add(
                new JLabel("Passengers")
        );

        cmbPassengerCount =
                new JComboBox<>();

        for (int i = 1; i <= 6; i++) {

            cmbPassengerCount.addItem(i);
        }

        formPanel.add(cmbPassengerCount);

        // ==========================================
        // Reservation Name
        // ==========================================

        formPanel.add(
                new JLabel("Reservation Name")
        );

        txtPassengerName =
                new JTextField();

        formPanel.add(txtPassengerName);

        // ==========================================
        // Train Number
        // ==========================================

        formPanel.add(
                new JLabel("Train Number")
        );

        txtTrainNumber =
                new JTextField();

        formPanel.add(txtTrainNumber);

        // ==========================================
        // Train Name
        // ==========================================

        formPanel.add(
                new JLabel("Train Name")
        );

        txtTrainName =
                new JTextField();

        txtTrainName.setEditable(false);

        formPanel.add(txtTrainName);

        // ==========================================
        // Class
        // ==========================================

        formPanel.add(
                new JLabel("Class")
        );

        cmbClassType =
                new JComboBox<>();

        cmbClassType.addItem("Sleeper");
        cmbClassType.addItem("3AC");
        cmbClassType.addItem("2AC");
        cmbClassType.addItem("1AC");

        formPanel.add(cmbClassType);

        // ==========================================
        // Fare
        // ==========================================

        formPanel.add(
                new JLabel("Total Fare")
        );

        txtFare =
                new JTextField();

        txtFare.setEditable(false);

        formPanel.add(txtFare);

        // ==========================================
        // Journey Date
        // ==========================================

        formPanel.add(
                new JLabel("Journey Date")
        );

        txtJourneyDate =
                new JTextField();

        txtJourneyDate.setToolTipText(
                "Enter date as dd/MM/yyyy or yyyy-MM-dd"
        );

        formPanel.add(txtJourneyDate);

        // ==========================================
        // Source
        // ==========================================

        formPanel.add(
                new JLabel("Source")
        );

        txtSource =
                new JTextField();

        formPanel.add(txtSource);

        // ==========================================
        // Destination
        // ==========================================

        formPanel.add(
                new JLabel("Destination")
        );

        txtDestination =
                new JTextField();

        formPanel.add(txtDestination);

        add(
                formPanel,
                BorderLayout.NORTH
        );

        // ==========================================
        // Passenger Panel
        // ==========================================

        passengerPanel =
                new JPanel();

        passengerPanel.setLayout(
                new BoxLayout(
                        passengerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        passengerPanel
                );

        scrollPane.setPreferredSize(
                new Dimension(
                        600,
                        300
                )
        );

        add(
                scrollPane,
                BorderLayout.CENTER
        );

        // ==========================================
        // Book Button
        // ==========================================

        btnBook =
                new JButton("Book Ticket");

        btnBook.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        JPanel bottomPanel =
                new JPanel();

        bottomPanel.add(btnBook);

        add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        // ==========================================
        // Listeners
        // ==========================================

        cmbPassengerCount.addActionListener(e -> {

            generatePassengerFields(
                    (Integer)
                            cmbPassengerCount
                                    .getSelectedItem()
            );

            updateFare();
        });

        cmbClassType.addActionListener(
                e -> updateFare()
        );

        txtTrainNumber.addFocusListener(
                new FocusAdapter() {

                    @Override
                    public void focusLost(
                            FocusEvent e
                    ) {

                        updateTrainName();
                    }
                }
        );

        btnBook.addActionListener(
                e -> bookTicket()
        );

        // ==========================================
        // Initial State
        // ==========================================

        generatePassengerFields(1);

        updateFare();

        setVisible(true);
    }

    // ==========================================
    // Train Name Lookup
    // ==========================================

    private void updateTrainName() {

        String input =
                txtTrainNumber
                        .getText()
                        .trim();

        if (input.isEmpty()) {

            txtTrainName.setText("");

            return;
        }

        try {

            int trainNumber =
                    Integer.parseInt(input);

            if (trainNumber <= 0) {

                txtTrainName.setText(
                        "Invalid Train Number"
                );

                return;
            }

            String trainName =
                    TrainData.getTrainName(
                            trainNumber
                    );

            if (trainName != null) {

                txtTrainName.setText(
                        trainName
                );

            } else {

                txtTrainName.setText(
                        "Train Not Found"
                );
            }

        } catch (NumberFormatException ex) {

            txtTrainName.setText(
                    "Invalid Train Number"
            );
        }
    }

    // ==========================================
    // Passenger Panel
    // ==========================================

    private void generatePassengerFields(
            int count
    ) {

        passengerPanel.removeAll();

        passengerNameFields.clear();

        passengerAgeFields.clear();

        passengerGenderFields.clear();

        for (int i = 1; i <= count; i++) {

            JPanel panel =
                    new JPanel(
                            new GridLayout(
                                    3,
                                    2,
                                    5,
                                    5
                            )
                    );

            panel.setBorder(
                    BorderFactory.createTitledBorder(
                            "Passenger " + i
                    )
            );

            panel.add(
                    new JLabel("Name")
            );

            JTextField txtName =
                    new JTextField();

            panel.add(txtName);

            panel.add(
                    new JLabel("Age")
            );

            JTextField txtAge =
                    new JTextField();

            panel.add(txtAge);

            panel.add(
                    new JLabel("Gender")
            );

            JComboBox<String> gender =
                    new JComboBox<>();

            gender.addItem("Male");
            gender.addItem("Female");
            gender.addItem("Other");

            panel.add(gender);

            passengerNameFields.add(
                    txtName
            );

            passengerAgeFields.add(
                    txtAge
            );

            passengerGenderFields.add(
                    gender
            );

            passengerPanel.add(panel);
        }

        passengerPanel.revalidate();

        passengerPanel.repaint();
    }

    // ==========================================
    // Fare
    // ==========================================

    private void updateFare() {

        if (cmbClassType == null
                || cmbPassengerCount == null
                || txtFare == null) {

            return;
        }

        String classType =
                cmbClassType
                        .getSelectedItem()
                        .toString();

        int passengers =
                (Integer)
                        cmbPassengerCount
                                .getSelectedItem();

        double fare =
                FareCalculator.calculateFare(
                        classType
                );

        txtFare.setText(
                String.format(
                        "%.2f",
                        fare * passengers
                )
        );
    }

    // ==========================================
    // BOOK TICKET
    // ==========================================

    private void bookTicket() {

        // ==========================================
        // Read Main Fields
        // ==========================================

        String reservationName =
                txtPassengerName
                        .getText()
                        .trim();

        String trainNumberText =
                txtTrainNumber
                        .getText()
                        .trim();

        String trainName =
                txtTrainName
                        .getText()
                        .trim();

        String classType =
                cmbClassType
                        .getSelectedItem()
                        .toString();

        String journeyDate =
                txtJourneyDate
                        .getText()
                        .trim();

        String source =
                txtSource
                        .getText()
                        .trim();

        String destination =
                txtDestination
                        .getText()
                        .trim();

        int passengerCount =
                (Integer)
                        cmbPassengerCount
                                .getSelectedItem();

        // ==========================================
        // Reservation Name Validation
        // ==========================================

        if (reservationName.isEmpty()) {

            showError(
                    "Please enter the reservation name."
            );

            txtPassengerName.requestFocus();

            return;
        }

        if (!isValidName(reservationName)) {

            showError(
                    "Reservation name can contain only letters and spaces."
            );

            txtPassengerName.requestFocus();

            return;
        }

        // ==========================================
        // Train Number Validation
        // ==========================================

        if (trainNumberText.isEmpty()) {

            showError(
                    "Please enter the train number."
            );

            txtTrainNumber.requestFocus();

            return;
        }

        int trainNumber;

        try {

            trainNumber =
                    Integer.parseInt(
                            trainNumberText
                    );

        } catch (NumberFormatException ex) {

            showError(
                    "Train number must contain only digits."
            );

            txtTrainNumber.requestFocus();

            return;
        }

        if (trainNumber <= 0) {

            showError(
                    "Train number must be greater than zero."
            );

            txtTrainNumber.requestFocus();

            return;
        }

        // ==========================================
        // Train Validation
        // ==========================================

        if (trainName.isEmpty()
                || trainName.equals(
                        "Train Not Found"
                )
                || trainName.equals(
                        "Invalid Train Number"
                )) {

            showError(
                    "Please enter a valid train number."
            );

            txtTrainNumber.requestFocus();

            return;
        }

        // ==========================================
        // Journey Date Validation
        // ==========================================

        if (journeyDate.isEmpty()) {

            showError(
                    "Please enter the journey date."
            );

            txtJourneyDate.requestFocus();

            return;
        }

        LocalDate parsedJourneyDate =
                parseJourneyDate(journeyDate);

        if (parsedJourneyDate == null) {

            showError(
                    "Invalid journey date.\n\n"
                            + "Use either:\n"
                            + "dd/MM/yyyy\n"
                            + "or\n"
                            + "yyyy-MM-dd"
            );

            txtJourneyDate.requestFocus();

            return;
        }

        if (parsedJourneyDate.isBefore(
                LocalDate.now()
        )) {

            showError(
                    "Journey date cannot be in the past."
            );

            txtJourneyDate.requestFocus();

            return;
        }

        // ==========================================
        // Source Validation
        // ==========================================

        if (source.isEmpty()) {

            showError(
                    "Please enter the source station."
            );

            txtSource.requestFocus();

            return;
        }

        if (!isValidLocation(source)) {

            showError(
                    "Source station contains invalid characters."
            );

            txtSource.requestFocus();

            return;
        }

        // ==========================================
        // Destination Validation
        // ==========================================

        if (destination.isEmpty()) {

            showError(
                    "Please enter the destination station."
            );

            txtDestination.requestFocus();

            return;
        }

        if (!isValidLocation(destination)) {

            showError(
                    "Destination station contains invalid characters."
            );

            txtDestination.requestFocus();

            return;
        }

        if (source.equalsIgnoreCase(
                destination
        )) {

            showError(
                    "Source and destination cannot be the same."
            );

            txtDestination.requestFocus();

            return;
        }

        // ==========================================
        // Passenger Count Validation
        // ==========================================

        if (passengerCount < 1
                || passengerCount > 6) {

            showError(
                    "Passenger count must be between 1 and 6."
            );

            return;
        }

        
        

        // ==========================================
        // Passenger Validation
        // ==========================================

        List<Passenger> passengers =
                new ArrayList<>();

        for (int i = 0;
             i < passengerNameFields.size();
             i++) {

            String name =
                    passengerNameFields
                            .get(i)
                            .getText()
                            .trim();

            String ageText =
                    passengerAgeFields
                            .get(i)
                            .getText()
                            .trim();

            // --------------------------------------
            // Passenger Name
            // --------------------------------------

            if (name.isEmpty()) {

                showError(
                        "Please enter the name for Passenger "
                                + (i + 1)
                                + "."
                );

                passengerNameFields
                        .get(i)
                        .requestFocus();

                return;
            }

            if (!isValidName(name)) {

                showError(
                        "Passenger "
                                + (i + 1)
                                + " name can contain only letters and spaces."
                );

                passengerNameFields
                        .get(i)
                        .requestFocus();

                return;
            }

            // --------------------------------------
            // Passenger Age
            // --------------------------------------

            if (ageText.isEmpty()) {

                showError(
                        "Please enter the age for Passenger "
                                + (i + 1)
                                + "."
                );

                passengerAgeFields
                        .get(i)
                        .requestFocus();

                return;
            }

            int age;

            try {

                age =
                        Integer.parseInt(
                                ageText
                        );

            } catch (NumberFormatException ex) {

                showError(
                        "Age for Passenger "
                                + (i + 1)
                                + " must be a number."
                );

                passengerAgeFields
                        .get(i)
                        .requestFocus();

                return;
            }

            if (age < 1 || age > 120) {

                showError(
                        "Age for Passenger "
                                + (i + 1)
                                + " must be between 1 and 120."
                );

                passengerAgeFields
                        .get(i)
                        .requestFocus();

                return;
            }

            // --------------------------------------
            // Gender
            // --------------------------------------

            String gender =
                    passengerGenderFields
                            .get(i)
                            .getSelectedItem()
                            .toString();

            // --------------------------------------
            // Create Passenger
            // --------------------------------------

            passengers.add(
                    new Passenger(
                            "",
                            name,
                            age,
                            gender,
                            classType,
                            SeatAllocator.getNextSeat(
                                    classType
                            )
                    )
            );
        }

        // ==========================================
        // Generate PNR
        // ==========================================

        String pnr =
                PNRGenerator.generatePNR();

        // ==========================================
        // Total Fare
        // ==========================================

        double totalFare;

        try {

            totalFare =
                    Double.parseDouble(
                            txtFare.getText()
                    );

        } catch (NumberFormatException ex) {

            showError(
                    "Unable to calculate the ticket fare."
            );

            return;
        }

        // ==========================================
        // Create Reservation
        // ==========================================

        Reservation reservation =
                new Reservation(
                        pnr,
                        reservationName,
                        trainNumber,
                        trainName,
                        classType,
                        totalFare,
                        journeyDate,
                        source,
                        destination
                );

        // ==========================================
        // Assign PNR to Passengers
        // ==========================================

        for (Passenger passenger : passengers) {

            passenger.setPnr(pnr);
        }

        // ==========================================
        // Booking Service
        // ==========================================

        BookingService bookingService =
                new BookingService();

        boolean success =
                bookingService.bookReservation(
                        reservation,
                        passengers
                );

        if (!success) {

            showError(
                    "Booking failed.\n\n"
                            + "The reservation could not be completed."
            );

            return;
        }

        // ==========================================
        // Success
        // ==========================================

        JOptionPane.showMessageDialog(
                this,
                "Reservation Successful!\n\n"
                        + "PNR: "
                        + pnr
                        + "\n"
                        + "Train: "
                        + trainName
                        + "\n"
                        + "Passengers: "
                        + passengerCount
                        + "\n"
                        + "Total Fare: ₹"
                        + String.format(
                                "%.2f",
                                totalFare
                        ),
                "Booking Successful",
                JOptionPane.INFORMATION_MESSAGE
        );

        new TicketFrame(
                reservation,
                passengers
        );

        clearFields();
    }

    // ==========================================
    // Validate Name
    // ==========================================

    private boolean isValidName(String name) {

        return name.matches(
                "[a-zA-Z ]+"
        );
    }

    // ==========================================
    // Validate Location
    // ==========================================

    private boolean isValidLocation(
            String location
    ) {

        return location.matches(
                "[a-zA-Z0-9 .'-]+"
        );
    }

    // ==========================================
    // Parse Journey Date
    // ==========================================

    private LocalDate parseJourneyDate(
            String date
    ) {

        DateTimeFormatter[] formatters = {

                DateTimeFormatter.ofPattern(
                        "dd/MM/yyyy"
                ),

                DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd"
                )
        };

        for (DateTimeFormatter formatter :
                formatters) {

            try {

                return LocalDate.parse(
                        date,
                        formatter
                );

            } catch (DateTimeParseException ignored) {

                // Try next format
            }
        }

        return null;
    }

    // ==========================================
    // Error Dialog
    // ==========================================

    private void showError(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Validation Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // ==========================================
    // Clear Fields
    // ==========================================

    private void clearFields() {

        txtPassengerName.setText("");

        txtTrainNumber.setText("");

        txtTrainName.setText("");

        txtJourneyDate.setText("");

        txtSource.setText("");

        txtDestination.setText("");

        cmbPassengerCount.setSelectedIndex(0);

        cmbClassType.setSelectedIndex(0);

        generatePassengerFields(1);

        updateFare();
    }
}