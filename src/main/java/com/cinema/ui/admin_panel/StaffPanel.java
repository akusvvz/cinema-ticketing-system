package com.cinema.ui.admin_panel;

import com.cinema.entities.Users;
import com.cinema.ui.admin_panel.manage_tickets.ManageTicketsFrame;

import javax.swing.*;
import java.awt.*;

// staff panel with access restricted to ticket management only
public class StaffPanel extends AdminPanel {
    public StaffPanel(Users user) {
        super(user);
    }

    @Override
    protected JPanel createButtonsPanel() {
        JPanel panel = new JPanel();

        // button to manage tickets
        JButton manageTicketsButton = new JButton("Manage tickets");
        manageTicketsButton.setPreferredSize(new Dimension(150, 40));

        // open ticket management frame on button click
        manageTicketsButton.addActionListener(e -> new ManageTicketsFrame());

        // add button to panel
        panel.add(manageTicketsButton);

        return panel;
    }
}