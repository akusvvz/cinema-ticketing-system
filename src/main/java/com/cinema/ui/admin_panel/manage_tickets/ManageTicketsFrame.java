package com.cinema.ui.admin_panel.manage_tickets;

import com.cinema.dao.MoviesDAO;
import com.cinema.dao.SeatsDAO;
import com.cinema.dao.ShowtimesDAO;
import com.cinema.dao.TicketsDAO;
import com.cinema.entities.Seats;
import com.cinema.entities.Showtimes;
import com.cinema.entities.Tickets;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// frame for managing tickets (add, edit, delete)
public class ManageTicketsFrame extends JFrame {
    private JTable ticketsTable;
    private DefaultTableModel tableModel;
    private TicketsDAO ticketsDAO;
    private SeatsDAO seatsDAO;
    private ShowtimesDAO showtimesDAO;
    private MoviesDAO moviesDAO;

    public ManageTicketsFrame() {
        setTitle("Manage Tickets");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // title label for the frame
        JLabel titleLabel = new JLabel("Manage Tickets");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // table configuration
        String[] columnNames = {"Ticket №", "Seat row", "Seat number", "Price", "Showtime"};
        tableModel = new DefaultTableModel(columnNames, 0);
        ticketsTable = new JTable(tableModel);
        ticketsTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        ticketsTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        ticketsTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        ticketsTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        ticketsTable.getColumnModel().getColumn(4).setPreferredWidth(250);
        JScrollPane scrollPane = new JScrollPane(ticketsTable);
        add(scrollPane, BorderLayout.CENTER);

        // initialize DAOs
        ticketsDAO = new TicketsDAO();
        seatsDAO = new SeatsDAO();
        showtimesDAO = new ShowtimesDAO();
        moviesDAO = new MoviesDAO();
        loadTickets();

        // buttons for managing tickets
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Ticket");
        JButton editButton = new JButton("✎");
        JButton deleteButton = new JButton("❌");

        addButton.addActionListener(e -> openTicketForm(null));
        editButton.addActionListener(e -> editSelectedTicket());
        deleteButton.addActionListener(e -> deleteSelectedTicket());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // loads tickets into the table
    public void loadTickets() {
        List<Tickets> tickets = ticketsDAO.getAllTickets();
        tableModel.setRowCount(0);

        for (Tickets ticket : tickets) {
            Seats seat = seatsDAO.getSeatById(ticket.getSeatId());
            Showtimes showtime = showtimesDAO.getShowtimeById(ticket.getShowtimeId());
            String showtimeInfo = showtime.getMovieTitle() + " | " +
                    showtime.getShowDate() + " | " +
                    showtime.getShowTime() + " | " +
                    showtime.getHallName();
            tableModel.addRow(new Object[]{
                    ticket.getTicketId(),
                    seat.getRowNumber(),
                    seat.getSeatNumber(),
                    ticket.getPrice(),
                    showtimeInfo
            });
        }
    }

    // opens the form to add or edit a ticket
    private void openTicketForm(Tickets ticket) {
        new TicketForm(this, ticket);
    }

    // retrieves selected ticket for editing
    private void editSelectedTicket() {
        int selectedRow = ticketsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a ticket to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int ticketId = (int) tableModel.getValueAt(selectedRow, 0);
        Tickets ticket = ticketsDAO.getTicketById(ticketId);
        openTicketForm(ticket);
    }

    // deletes the selected ticket
    private void deleteSelectedTicket() {
        int selectedRow = ticketsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a ticket to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int ticketId = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Delete Ticket", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            ticketsDAO.deleteTicket(ticketId);
            loadTickets();
        }
    }
}
