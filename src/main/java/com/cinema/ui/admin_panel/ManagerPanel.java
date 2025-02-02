package com.cinema.ui.admin_panel;

import com.cinema.entities.Users;
import com.cinema.ui.admin_panel.manage_movies.ManageMoviesFrame;
import com.cinema.ui.admin_panel.manage_showtimes.ManageShowtimesFrame;
import com.cinema.ui.admin_panel.manage_tickets.ManageTicketsFrame;

import javax.swing.*;
import java.awt.*;

// manager panel with access to movie, showtime, and ticket management
public class ManagerPanel extends AdminPanel {
    public ManagerPanel(Users user) {
        super(user);
    }

    @Override
    protected JPanel createButtonsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // button to manage tickets
        JButton manageTicketsButton = new JButton("Manage tickets");
        manageTicketsButton.setPreferredSize(new Dimension(150, 40));

        // button to manage movies
        JButton manageMoviesButton = new JButton("Manage movies");
        manageMoviesButton.setPreferredSize(new Dimension(150, 40));

        // button to manage showtimes
        JButton manageShowtimesButton = new JButton("Manage showtimes");
        manageShowtimesButton.setPreferredSize(new Dimension(150, 40));

        // open respective management frames on button click
        manageTicketsButton.addActionListener(e -> new ManageTicketsFrame());
        manageShowtimesButton.addActionListener(e -> new ManageShowtimesFrame());
        manageMoviesButton.addActionListener(e -> new ManageMoviesFrame());

        // add buttons to panel
        panel.add(manageTicketsButton);
        panel.add(manageMoviesButton);
        panel.add(manageShowtimesButton);

        return panel;
    }
}
