package com.abhimanyu.reservation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SeatInventoryFrame extends JFrame {

    private JTable inventoryTable;

    private final SeatInventoryDAO seatInventoryDAO =
            new SeatInventoryDAO();

    public SeatInventoryFrame() {

        setTitle("IRON RAIL - Seat Inventory");

        setSize(700, 500);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLayout(new BorderLayout(10, 10));

        // ==========================================
        // Header
        // ==========================================

        JLabel titleLabel =
                new JLabel(
                        "IRON RAIL - SEAT INVENTORY",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        titleLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        10,
                        15,
                        10
                )
        );

        add(
                titleLabel,
                BorderLayout.NORTH
        );

        // ==========================================
        // Table
        // ==========================================

        inventoryTable =
                new JTable();

        inventoryTable.setRowHeight(28);

        inventoryTable.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        inventoryTable
                .getTableHeader()
                .setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                14
                        )
                );

        inventoryTable.setAutoCreateRowSorter(true);

        JScrollPane scrollPane =
                new JScrollPane(
                        inventoryTable
                );

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

        JButton refreshButton =
                new JButton("Refresh");

        JButton closeButton =
                new JButton("Close");

        refreshButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        closeButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        refreshButton.addActionListener(
                event -> loadInventory()
        );

        closeButton.addActionListener(
                event -> dispose()
        );

        buttonPanel.add(
                refreshButton
        );

        buttonPanel.add(
                closeButton
        );

        add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // ==========================================
        // Load Data
        // ==========================================

        loadInventory();

        setVisible(true);
    }

    // ==========================================
    // Load Inventory
    // ==========================================

    private void loadInventory() {

        try {

            List<Object[]> inventory =
                    seatInventoryDAO
                            .getAllSeatInventory();

            String[] columns = {
                    "Train Number",
                    "Class",
                    "Available Seats"
            };

            Object[][] data =
                    new Object[
                            inventory.size()
                    ][columns.length];

            for (
                    int i = 0;
                    i < inventory.size();
                    i++
            ) {

                Object[] row =
                        inventory.get(i);

                data[i][0] = row[0];

                data[i][1] = row[1];

                data[i][2] = row[2];
            }

            DefaultTableModel model =
                    new DefaultTableModel(
                            data,
                            columns
                    ) {

                        @Override
                        public boolean isCellEditable(
                                int row,
                                int column
                        ) {

                            return false;
                        }
                    };

            inventoryTable.setModel(model);

            inventoryTable
                    .getColumnModel()
                    .getColumn(0)
                    .setPreferredWidth(150);

            inventoryTable
                    .getColumnModel()
                    .getColumn(1)
                    .setPreferredWidth(150);

            inventoryTable
                    .getColumnModel()
                    .getColumn(2)
                    .setPreferredWidth(180);

        } catch (Exception exception) {

            exception.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load seat inventory.\n\n"
                            + exception.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}