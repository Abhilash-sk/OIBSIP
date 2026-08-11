package com.abhimanyu.reservation;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PdfTicketExporter {

    private PdfTicketExporter() {
        // Utility class
    }

    public static void exportTicket(
            Reservation reservation,
            List<Passenger> passengers,
            JFrame parent
    ) {

        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setDialogTitle(
                "Save Railway Ticket as PDF"
        );

        fileChooser.setSelectedFile(
                new File(
                        "Ticket_" +
                        reservation.getPnr() +
                        ".pdf"
                )
        );

        int result =
                fileChooser.showSaveDialog(parent);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File outputFile =
                fileChooser.getSelectedFile();

        try (PDDocument document = new PDDocument()) {

            PDPage page =
                    new PDPage(PDRectangle.A4);

            document.addPage(page);

            PDType1Font boldFont =
                    new PDType1Font(
                            Standard14Fonts.FontName.HELVETICA_BOLD
                    );

            PDType1Font regularFont =
                    new PDType1Font(
                            Standard14Fonts.FontName.HELVETICA
                    );

            float margin = 50;

            float pageWidth =
                    page.getMediaBox().getWidth();

            float pageHeight =
                    page.getMediaBox().getHeight();

            float y = pageHeight - 50;

            // ==========================================
            // QR DATA
            // ==========================================

            String qrData =
                    "PNR: " +
                    reservation.getPnr() + "\n" +

                    "Train: " +
                    reservation.getTrainName() + "\n" +

                    "Train No: " +
                    reservation.getTrainNumber() + "\n" +

                    "Journey: " +
                    reservation.getSource() +
                    " -> " +
                    reservation.getDestination() + "\n" +

                    "Date: " +
                    reservation.getJourneyDate() + "\n" +

                    "Class: " +
                    reservation.getClassType() + "\n" +

                    "Passengers: " +
                    passengers.size() + "\n" +

                    "Fare: INR " +
                    reservation.getTotalFare();

            BufferedImage qrImage =
                    QRCodeGenerator.generateQRCodeImage(
                            qrData,
                            180,
                            180
                    );

            PDImageXObject qrPdfImage =
                    LosslessFactory.createFromImage(
                            document,
                            qrImage
                    );

            PDPageContentStream contentStream =
                    new PDPageContentStream(
                            document,
                            page
                    );

            // ==========================================
            // HEADER
            // ==========================================

            contentStream.beginText();

            contentStream.setFont(
                    boldFont,
                    18
            );

            contentStream.newLineAtOffset(
                    margin,
                    y
            );

            contentStream.showText(
                    "INDIAN RAILWAYS E-TICKET"
            );

            contentStream.endText();

            y -= 35;

            drawLine(
                    contentStream,
                    margin,
                    pageWidth - margin,
                    y
            );

            y -= 30;

            // ==========================================
            // BOOKING STATUS
            // ==========================================

            y = writeLine(
                    contentStream,
                    boldFont,
                    regularFont,
                    "Booking Status",
                    "CONFIRMED",
                    margin,
                    y
            );

            y = writeLine(
                    contentStream,
                    boldFont,
                    regularFont,
                    "PNR",
                    reservation.getPnr(),
                    margin,
                    y
            );

            y = writeLine(
                    contentStream,
                    boldFont,
                    regularFont,
                    "Passenger Count",
                    String.valueOf(
                            passengers.size()
                    ),
                    margin,
                    y
            );

            // ==========================================
            // RESERVATION DETAILS
            // ==========================================

            y = writeLine(
                    contentStream,
                    boldFont,
                    regularFont,
                    "Train Number",
                    String.valueOf(
                            reservation.getTrainNumber()
                    ),
                    margin,
                    y
            );

            y = writeLine(
                    contentStream,
                    boldFont,
                    regularFont,
                    "Train Name",
                    reservation.getTrainName(),
                    margin,
                    y
            );

            y = writeLine(
                    contentStream,
                    boldFont,
                    regularFont,
                    "Class",
                    reservation.getClassType(),
                    margin,
                    y
            );

            y = writeLine(
                    contentStream,
                    boldFont,
                    regularFont,
                    "Journey Date",
                    reservation.getJourneyDate(),
                    margin,
                    y
            );

            y = writeLine(
                    contentStream,
                    boldFont,
                    regularFont,
                    "Source",
                    reservation.getSource(),
                    margin,
                    y
            );

            y = writeLine(
                    contentStream,
                    boldFont,
                    regularFont,
                    "Destination",
                    reservation.getDestination(),
                    margin,
                    y
            );

            y = writeLine(
                    contentStream,
                    boldFont,
                    regularFont,
                    "Total Fare",
                    "INR " +
                    String.format(
                            "%.2f",
                            reservation.getTotalFare()
                    ),
                    margin,
                    y
            );

            // ==========================================
            // QR CODE
            // ==========================================

            contentStream.drawImage(
                    qrPdfImage,
                    pageWidth - 180,
                    pageHeight - 250,
                    120,
                    120
            );

            y -= 15;

            drawLine(
                    contentStream,
                    margin,
                    pageWidth - margin,
                    y
            );

            y -= 25;

            // ==========================================
            // PASSENGER HEADING
            // ==========================================

            contentStream.beginText();

            contentStream.setFont(
                    boldFont,
                    14
            );

            contentStream.newLineAtOffset(
                    margin,
                    y
            );

            contentStream.showText(
                    "PASSENGER DETAILS"
            );

            contentStream.endText();

            y -= 30;

            // ==========================================
            // PASSENGER DETAILS
            // ==========================================

            int passengerNumber = 1;

            for (Passenger passenger : passengers) {

                if (y < 120) {

                    contentStream.close();

                    page =
                            new PDPage(
                                    PDRectangle.A4
                            );

                    document.addPage(page);

                    pageWidth =
                            page.getMediaBox()
                                    .getWidth();

                    contentStream =
                            new PDPageContentStream(
                                    document,
                                    page
                            );

                    y =
                            page.getMediaBox()
                                    .getHeight()
                                    - 50;
                }

                y = writeLine(
                        contentStream,
                        boldFont,
                        regularFont,
                        "Passenger",
                        String.valueOf(
                                passengerNumber++
                        ),
                        margin,
                        y
                );

                y = writeLine(
                        contentStream,
                        boldFont,
                        regularFont,
                        "Name",
                        passenger.getPassengerName(),
                        margin + 20,
                        y
                );

                y = writeLine(
                        contentStream,
                        boldFont,
                        regularFont,
                        "Age",
                        String.valueOf(
                                passenger.getAge()
                        ),
                        margin + 20,
                        y
                );

                y = writeLine(
                        contentStream,
                        boldFont,
                        regularFont,
                        "Gender",
                        passenger.getGender(),
                        margin + 20,
                        y
                );

                y = writeLine(
                        contentStream,
                        boldFont,
                        regularFont,
                        "Coach",
                        passenger.getCoach(),
                        margin + 20,
                        y
                );

                y = writeLine(
                        contentStream,
                        boldFont,
                        regularFont,
                        "Seat",
                        passenger.getSeatNumber(),
                        margin + 20,
                        y
                );

                y -= 10;

                drawLine(
                        contentStream,
                        margin,
                        pageWidth - margin,
                        y
                );

                y -= 20;
            }

            // ==========================================
            // FOOTER
            // ==========================================

            contentStream.beginText();

            contentStream.setFont(
                    regularFont,
                    10
            );

            contentStream.newLineAtOffset(
                    margin,
                    40
            );

            contentStream.showText(
                    "Thank you for choosing our railway reservation service."
            );

            contentStream.endText();

            contentStream.close();

            // ==========================================
            // SAVE PDF
            // ==========================================

            document.save(outputFile);

            JOptionPane.showMessageDialog(
                    parent,
                    "Ticket saved successfully!\n\n"
                            + outputFile.getAbsolutePath(),
                    "PDF Export Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (IOException exception) {

            exception.printStackTrace();

            JOptionPane.showMessageDialog(
                    parent,
                    "Unable to create PDF.\n\n"
                            + exception.getMessage(),
                    "PDF Export Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================================
    // Write PDF Line
    // ==========================================

    private static float writeLine(
            PDPageContentStream contentStream,
            PDType1Font boldFont,
            PDType1Font regularFont,
            String label,
            String value,
            float x,
            float y
    ) throws IOException {

        contentStream.beginText();

        contentStream.setFont(
                boldFont,
                11
        );

        contentStream.newLineAtOffset(
                x,
                y
        );

        contentStream.showText(
                label + " : "
        );

        float labelWidth =
                boldFont.getStringWidth(
                        label + " : "
                ) / 1000 * 11;

        contentStream.setFont(
                regularFont,
                11
        );

        contentStream.newLineAtOffset(
                labelWidth,
                0
        );

        contentStream.showText(
                sanitize(value)
        );

        contentStream.endText();

        return y - 22;
    }

    // ==========================================
    // Draw Line
    // ==========================================

    private static void drawLine(
            PDPageContentStream contentStream,
            float startX,
            float endX,
            float y
    ) throws IOException {

        contentStream.moveTo(
                startX,
                y
        );

        contentStream.lineTo(
                endX,
                y
        );

        contentStream.stroke();
    }

    // ==========================================
    // Sanitize PDF Text
    // ==========================================

    private static String sanitize(
            String text
    ) {

        if (text == null) {
            return "";
        }

        return text
                .replace("₹", "INR ")
                .replace("\n", " ")
                .replace("\r", " ");
    }
}