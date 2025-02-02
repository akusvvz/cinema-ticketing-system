package com.cinema.ui.admin_panel.manage_tickets;

import com.cinema.dao.SeatsDAO;
import com.cinema.dao.ShowtimesDAO;
import com.cinema.dao.TicketsDAO;
import com.cinema.entities.Seats;
import com.cinema.entities.Showtimes;
import com.cinema.entities.Tickets;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// form for adding or updating ticket details
public class TicketForm extends JDialog {
    private JTextField seatRowField;
    private JTextField seatNumberField;
    private JTextField priceField;
    private JComboBox<Showtimes> showtimeDropdown;
    private TicketsDAO ticketsDAO;
    private ShowtimesDAO showtimesDAO;
    private SeatsDAO seatsDAO;
    private Tickets ticket;

    public TicketForm(JFrame parent, Tickets ticket) {
        super(parent, "Ticket Details", true);
        this.ticket = ticket;
        ticketsDAO = new TicketsDAO();
        showtimesDAO = new ShowtimesDAO();
        seatsDAO = new SeatsDAO();

        setSize(600, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 2, 10, 10));

        // input fields for seat row, number, price, and showtime
        add(new JLabel("Seat row:"));
        seatRowField = new JTextField();
        add(seatRowField);

        add(new JLabel("Seat number:"));
        seatNumberField = new JTextField();
        add(seatNumberField);

        add(new JLabel("Price:"));
        priceField = new JTextField();
        add(priceField);

        add(new JLabel("Showtime:"));
        showtimeDropdown = new JComboBox<>();
        loadShowtimes();
        add(showtimeDropdown);

        // save button for adding or updating a ticket
        JButton saveButton = new JButton(ticket == null ? "Add Ticket" : "Update Ticket");
        saveButton.addActionListener(e -> saveTicket());
        add(saveButton);

        // if updating, populate fields with existing ticket data
        if (ticket != null) {
            Seats seat = seatsDAO.getSeatById(ticket.getSeatId());
            seatRowField.setText(String.valueOf(seat.getRowNumber()));
            seatNumberField.setText(String.valueOf(seat.getSeatNumber()));
            priceField.setText(String.valueOf(ticket.getPrice()));

            for (int i = 0; i < showtimeDropdown.getItemCount(); i++) {
                Showtimes st = showtimeDropdown.getItemAt(i);
                if (st.getShowtimeId() == ticket.getShowtimeId()) {
                    showtimeDropdown.setSelectedIndex(i);
                    break;
                }
            }
        }

        setVisible(true);
    }

    // loads available showtimes into the dropdown list
    private void loadShowtimes() {
        List<Showtimes> showtimes = showtimesDAO.getAllShowtimes();
        showtimeDropdown.removeAllItems();
        for (Showtimes showtime : showtimes) {
            showtimeDropdown.addItem(showtime);
        }
    }

    // saves or updates ticket details
    private void saveTicket() {
        try {
            int seatRow = Integer.parseInt(seatRowField.getText());
            int seatNumber = Integer.parseInt(seatNumberField.getText());
            double price = Double.parseDouble(priceField.getText());

            Showtimes selectedShowtime = (Showtimes) showtimeDropdown.getSelectedItem();
            if (selectedShowtime == null) {
                JOptionPane.showMessageDialog(this, "Please select a showtime.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int showtimeId = selectedShowtime.getShowtimeId();
            int hallId = selectedShowtime.getHallId();
            int seatId = seatsDAO.getSeatIdByRowAndNumber(hallId, seatRow, seatNumber);

            if (seatId == -1) {
                JOptionPane.showMessageDialog(this, "Invalid seat selection!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // if adding a new ticket, check if the seat is already occupied
            if (ticket == null) {
                if (ticketsDAO.isSeatOccupied(showtimeId, seatId)) {
                    JOptionPane.showMessageDialog(this, "This seat is already occupied!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int newTicketId = ticketsDAO.saveTicket(showtimeId, seatId, price);
                if (newTicketId != -1) {
                    JOptionPane.showMessageDialog(this, "Ticket added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                // if updating, check if the new seat is occupied
                if (ticket.getSeatId() != seatId && ticketsDAO.isSeatOccupied(showtimeId, seatId)) {
                    JOptionPane.showMessageDialog(this, "This seat is already occupied!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ticket.setSeatId(seatId);
                ticket.setPrice(price);
                ticket.setShowtimeId(showtimeId);
                boolean updated = ticketsDAO.updateTicket(ticket);
                if (updated) {
                    JOptionPane.showMessageDialog(this, "Ticket updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            dispose();
            ((ManageTicketsFrame) getParent()).loadTickets();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please check seat and price values.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
