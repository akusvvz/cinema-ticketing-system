package com.cinema.ui.admin_panel;

import com.cinema.entities.Users;
import com.cinema.ui.admin_panel.manage_movies.ManageMoviesFrame;
import com.cinema.ui.admin_panel.manage_showtimes.ManageShowtimesFrame;
import com.cinema.ui.admin_panel.manage_tickets.ManageTicketsFrame;

import javax.swing.*;
import java.awt.*;

public class ManagerPanel extends AdminPanel {
    public ManagerPanel(Users user) {
        super(user);
    }

    @Override
    protected JPanel createButtonsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton manageTicketsButton = new JButton("Manage tickets");
        manageTicketsButton.setPreferredSize(new Dimension(150, 40));
        JButton manageMoviesButton = new JButton("Manage movies");
        manageMoviesButton.setPreferredSize(new Dimension(150, 40));
        JButton manageShowtimesButton = new JButton("Manage showtimes");
        manageShowtimesButton.setPreferredSize(new Dimension(150, 40));

        manageTicketsButton.addActionListener(e -> new ManageTicketsFrame());
        manageShowtimesButton.addActionListener(e -> new ManageShowtimesFrame());
        manageMoviesButton.addActionListener(e -> new ManageMoviesFrame());

        panel.add(manageTicketsButton);
        panel.add(manageMoviesButton);
        panel.add(manageShowtimesButton);

        return panel;
    }
}
