package com.cinema.ui.admin_panel.manage_showtimes;

import com.cinema.dao.ShowtimesDAO;
import com.cinema.dao.MoviesDAO;
import com.cinema.dao.HallsDAO;
import com.cinema.entities.Showtimes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// frame for managing showtimes (add, edit, delete)
public class ManageShowtimesFrame extends JFrame {
    private JTable showtimesTable;
    private DefaultTableModel tableModel;
    private ShowtimesDAO showtimesDAO;
    private MoviesDAO moviesDAO;
    private HallsDAO hallsDAO;

    public ManageShowtimesFrame() {
        setTitle("Manage Showtimes");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // header label
        JLabel titleLabel = new JLabel("Manage Showtimes");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // table setup
        String[] columnNames = {"ID", "Movie", "Hall", "Date", "Time"};
        tableModel = new DefaultTableModel(columnNames, 0);
        showtimesTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(showtimesTable);
        add(scrollPane, BorderLayout.CENTER);

        // initialize DAO instances
        showtimesDAO = new ShowtimesDAO();
        moviesDAO = new MoviesDAO();
        hallsDAO = new HallsDAO();

        loadShowtimes();

        // button panel for adding, editing, and deleting showtimes
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Showtime");
        JButton editButton = new JButton("✎");
        JButton deleteButton = new JButton("❌");

        addButton.addActionListener(e -> openShowtimeForm(null));
        editButton.addActionListener(e -> editSelectedShowtime());
        deleteButton.addActionListener(e -> deleteSelectedShowtime());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // loads showtimes into the table
    public void loadShowtimes() {
        List<Showtimes> showtimes = showtimesDAO.getAllShowtimes();
        tableModel.setRowCount(0);
        for (Showtimes st : showtimes) {
            tableModel.addRow(new Object[]{
                    st.getShowtimeId(),
                    st.getMovieTitle(),
                    st.getHallName(),
                    st.getShowDate(),
                    st.getShowTime()
            });
        }
    }

    // opens the form to add or edit a showtime
    private void openShowtimeForm(Showtimes showtime) {
        new ShowtimeForm(this, showtime);
    }

    // handles editing a selected showtime
    private void editSelectedShowtime() {
        int selectedRow = showtimesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a showtime to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int showtimeId = (int) tableModel.getValueAt(selectedRow, 0);
        Showtimes showtime = showtimesDAO.getShowtimeById(showtimeId);
        openShowtimeForm(showtime);
    }

    // handles deleting a selected showtime
    private void deleteSelectedShowtime() {
        int selectedRow = showtimesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a showtime to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int showtimeId = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this showtime?", "Delete Showtime", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean deleted = showtimesDAO.deleteShowtime(showtimeId);
            if (deleted) {
                JOptionPane.showMessageDialog(this, "Showtime deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadShowtimes();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete showtime.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
