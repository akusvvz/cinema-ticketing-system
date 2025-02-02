package com.cinema.ui.admin_panel;

import com.cinema.entities.Users;
import com.cinema.ui.admin_panel.manage_tickets.ManageTicketsFrame;

import javax.swing.*;
import java.awt.*;

public class StaffPanel extends AdminPanel {
    public StaffPanel(Users user) {
        super(user);
    }

    @Override
    protected JPanel createButtonsPanel() {
        JPanel panel = new JPanel();
        JButton manageTicketsButton = new JButton("Manage tickets");
        manageTicketsButton.setPreferredSize(new Dimension(150, 40));
        manageTicketsButton.addActionListener(e -> new ManageTicketsFrame());
        panel.add(manageTicketsButton);
        return panel;
    }
}
