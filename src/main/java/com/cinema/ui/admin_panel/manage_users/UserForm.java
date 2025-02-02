package com.cinema.ui.admin_panel.manage_users;

import com.cinema.dao.UsersDAO;
import com.cinema.entities.Users;

import javax.swing.*;
import java.awt.*;

// form for adding or editing a user
public class UserForm extends JDialog {
    private JTextField nameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JComboBox<String> roleDropdown;
    private JButton saveButton;

    private Users user;
    private UsersDAO usersDAO;

    public UserForm(JFrame parent, Users user) {
        super(parent, "User Details", true);
        this.user = user;
        usersDAO = new UsersDAO();

        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 2, 10, 10));

        // input field for name
        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        // input field for password
        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        // input field for email
        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        // dropdown for selecting user role
        add(new JLabel("Role:"));
        roleDropdown = new JComboBox<>();
        roleDropdown.addItem("admin");
        roleDropdown.addItem("manager");
        roleDropdown.addItem("staff");
        add(roleDropdown);

        // save button to add or update user
        saveButton = new JButton(user == null ? "Add User" : "Update User");
        saveButton.addActionListener(e -> saveUser());
        add(new JLabel()); // empty label for alignment
        add(saveButton);

        // pre-fill fields if editing an existing user
        if (user != null) {
            nameField.setText(user.getName());
            passwordField.setText(user.getPassword());
            emailField.setText(user.getEmail());
            roleDropdown.setSelectedItem(user.getRole());
        }

        setVisible(true);
    }

    // saves or updates user information
    private void saveUser() {
        try {
            String name = nameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String email = emailField.getText().trim();
            String role = (String) roleDropdown.getSelectedItem();

            // validate input fields
            if (name.isEmpty() || password.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (user == null) {
                // adding a new user
                Users newUser = new Users();
                newUser.setName(name);
                newUser.setPassword(password);
                newUser.setEmail(email);
                newUser.setRole(role);
                boolean added = usersDAO.addUser(newUser);
                if (added) {
                    JOptionPane.showMessageDialog(this, "User added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add user.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // updating an existing user
                user.setName(name);
                user.setPassword(password);
                user.setEmail(email);
                user.setRole(role);
                boolean updated = usersDAO.updateUser(user);
                if (updated) {
                    JOptionPane.showMessageDialog(this, "User updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update user.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            dispose();
            ((ManageUsersFrame) getParent()).loadUsers();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}