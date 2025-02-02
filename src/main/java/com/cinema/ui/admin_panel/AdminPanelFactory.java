package com.cinema.ui.admin_panel;

import com.cinema.entities.Users;

public class AdminPanelFactory {
    public static AdminPanel createAdminPanel(Users user) {
        switch (user.getRole().toLowerCase()) {
            case "admin":
                return new AdminPanelFull(user);
            case "manager":
                return new ManagerPanel(user);
            case "staff":
                return new StaffPanel(user);
            default:
                throw new IllegalArgumentException("Unknown role: " + user.getRole());
        }
    }
}
