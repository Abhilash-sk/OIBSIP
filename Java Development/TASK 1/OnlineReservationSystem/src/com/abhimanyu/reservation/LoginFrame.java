package com.abhimanyu.reservation;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private final UserDAO userDAO = new UserDAO();

    public LoginFrame() {

        setTitle("Online Reservation System");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(
                new GridLayout(3, 2, 10, 10)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 40, 30, 40
                )
        );

        // ==========================
        // Username
        // ==========================

        panel.add(new JLabel("Username"));

        usernameField = new JTextField();

        UITheme.styleField(usernameField);

        panel.add(usernameField);

        // ==========================
        // Password
        // ==========================

        panel.add(new JLabel("Password"));

        passwordField = new JPasswordField();

        passwordField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        panel.add(passwordField);

        // ==========================
        // Login Button
        // ==========================

        loginButton = new JButton("Login");

        UITheme.styleButton(loginButton);

        panel.add(loginButton);

        // ==========================
        // Login Action
        // ==========================

        loginButton.addActionListener(
                event -> login()
        );

        add(panel);

        setVisible(true);
    }

    // ==========================
    // Login
    // ==========================

    private void login() {

        String username =
                usernameField
                        .getText()
                        .trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        if (username.isEmpty() ||
                password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.",
                    "Login",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // ==========================
        // Authenticate from Database
        // ==========================

        User user =
                userDAO.authenticate(
                        username,
                        password
                );

        if (user == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Username or Password!",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            passwordField.setText("");

            return;
        }

        // ==========================
        // Check User Role
        // ==========================

        if ("ADMIN".equalsIgnoreCase(user.getRole())) {

            new AdminDashboardFrame();

        } else {

            new DashboardFrame();
        }

        dispose();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                LoginFrame::new
        );
    }
}