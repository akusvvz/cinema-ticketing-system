package com.cinema.ui.admin_panel.manage_users;

import com.cinema.dao.UsersDAO;
import com.cinema.entities.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// frame for managing users (view, add, edit, delete)
public class ManageUsersFrame extends JFrame {
    private JTable usersTable;
    private DefaultTableModel tableModel;
    private UsersDAO usersDAO;

    public ManageUsersFrame() {
        setTitle("Manage Users");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // title label
        JLabel titleLabel = new JLabel("Manage Users");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // table setup to display user details
        String[] columnNames = {"ID", "Name", "Password", "Email", "Role"};
        tableModel = new DefaultTableModel(columnNames, 0);
        usersTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(usersTable);
        add(scrollPane, BorderLayout.CENTER);

        // initialize database access object
        usersDAO = new UsersDAO();
        loadUsers();

        // action buttons for managing users
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add User");
        JButton editButton = new JButton("✎");
        JButton deleteButton = new JButton("❌");

        addButton.addActionListener(e -> openUserForm(null));
        editButton.addActionListener(e -> editSelectedUser());
        deleteButton.addActionListener(e -> deleteSelectedUser());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // loads all users from the database into the table
    public void loadUsers() {
        List<Users> users = usersDAO.getAllUsers();
        tableModel.setRowCount(0);
        for (Users user : users) {
            tableModel.addRow(new Object[]{
                    user.getUserId(),
                    user.getName(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getRole()
            });
        }
    }

    // opens form to add or edit a user
    private void openUserForm(Users user) {
        new UserForm(this, user);
    }

    // edits the selected user from the table
    private void editSelectedUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        Users user = usersDAO.getUserById(userId);
        openUserForm(user);
    }

    // deletes the selected user after confirmation
    private void deleteSelectedUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Delete User", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean deleted = usersDAO.deleteUser(userId);
            if (deleted) {
                JOptionPane.showMessageDialog(this, "User deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadUsers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete user.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}