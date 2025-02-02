package com.cinema.ui;

import com.cinema.dao.UsersDAO;
import com.cinema.entities.Users;
import com.cinema.ui.admin_panel.AdminPanel;
import com.cinema.ui.admin_panel.AdminPanelFactory;

import javax.swing.*;
import java.awt.*;

// login window for admin panel access
public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginFrame() {
        // set up window properties
        setTitle("Login");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // header label
        JLabel headerLabel = new JLabel("Login to Admin Panel", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(headerLabel, BorderLayout.NORTH);

        // form panel for email and password fields
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField(15);
        formPanel.add(emailField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(15);
        formPanel.add(passwordField);

        add(formPanel, BorderLayout.CENTER);

        // login button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());
        buttonPanel.add(loginButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // set border around the content pane
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setVisible(true);
    }

    // handles login authentication
    private void login() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        UsersDAO userDAO = new UsersDAO();
        Users user = userDAO.authenticateUser(email, password);

        if (user != null) {
            // open the appropriate admin panel based on the user role
            AdminPanel panel = AdminPanelFactory.createAdminPanel(user);
            panel.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid email or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
