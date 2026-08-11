package com.abhimanyu.reservation;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public final class UITheme {

    public static final Color PRIMARY =
            new Color(13, 71, 161);

    public static final Color SECONDARY =
            new Color(25, 118, 210);

    public static final Color BACKGROUND =
            Color.WHITE;

    public static final Color CARD =
            new Color(245, 247, 250);

    public static final Color BORDER =
            new Color(210, 210, 210);

    private UITheme() {
    }

    public static void styleButton(JButton button) {

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        button.setBackground(SECONDARY);

        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setOpaque(true);

        button.setCursor(
                new java.awt.Cursor(
                        java.awt.Cursor.HAND_CURSOR
                )
        );
    }

    public static void styleTitle(JLabel label) {

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        label.setForeground(PRIMARY);
    }

    public static void styleField(JTextField field) {

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );
    }
}