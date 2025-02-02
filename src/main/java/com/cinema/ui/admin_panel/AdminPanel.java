package com.cinema.ui.admin_panel;

import com.cinema.entities.Users;

import javax.swing.*;
import java.awt.*;

public abstract class AdminPanel extends JFrame {
    protected Users user;

    public AdminPanel(Users user) {
        this.user = user;
        setTitle(user.getRole());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Hello, " + user.getRole().toLowerCase() + ", " + user.getName());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(welcomeLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(createButtonsPanel(), BorderLayout.WEST);

        setVisible(true);
    }

    protected abstract JPanel createButtonsPanel();
}
