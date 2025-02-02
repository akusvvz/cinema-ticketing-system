package com.cinema.ui.admin_panel;

import com.cinema.entities.Users;

import javax.swing.*;
import java.awt.*;

// base class for admin panels with role-based UI
public abstract class AdminPanel extends JFrame {
    protected Users user;

    public AdminPanel(Users user) {
        this.user = user;
        setTitle(user.getRole()); // set window title to the user's role
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // top panel with welcome message
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Hello, " + user.getRole().toLowerCase() + ", " + user.getName());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(welcomeLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(createButtonsPanel(), BorderLayout.WEST); // add role-specific buttons panel

        setVisible(true);
    }

    // abstract method to be implemented by subclasses for role-specific buttons
    protected abstract JPanel createButtonsPanel();
}
