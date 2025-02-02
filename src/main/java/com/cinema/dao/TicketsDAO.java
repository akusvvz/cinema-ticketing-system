package com.cinema.dao;

import com.cinema.dao.interfaces.ITicketsDAO;
import com.cinema.db.DBConnectionManager;
import com.cinema.entities.Tickets;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// tickets dao: handles CRUD operations for tickets
public class TicketsDAO implements ITicketsDAO {

    private Connection connection;

    public TicketsDAO() {
        try {
            // obtain a database connection
            this.connection = DBConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Tickets> getAllTickets() {
        List<Tickets> tickets = new ArrayList<>();
        String query = "SELECT * FROM tickets";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            // iterate through each ticket record
            while (resultSet.next()) {
                Tickets ticket = new Tickets();
                ticket.setTicketId(resultSet.getInt("ticket_id"));
                ticket.setShowtimeId(resultSet.getInt("showtime_id"));
                ticket.setSeatId(resultSet.getInt("seat_id"));
                ticket.setPrice(resultSet.getDouble("price"));
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public Tickets getTicketById(int id) {
        Tickets ticket = null;
        String query = "SELECT * FROM tickets WHERE ticket_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id); // set ticket id parameter
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ticket = new Tickets();
                ticket.setTicketId(resultSet.getInt("ticket_id"));
                ticket.setShowtimeId(resultSet.getInt("showtime_id"));
                ticket.setSeatId(resultSet.getInt("seat_id"));
                ticket.setPrice(resultSet.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticket;
    }

    @Override
    public int generateUniqueTicketId() {
        Random random = new Random();
        int ticketId;
        boolean exists;
        // generate a random ticket id until a unique one is found
        do {
            ticketId = 100000 + random.nextInt(900000);
            exists = checkTicketIdExists(ticketId);
        } while (exists);
        return ticketId;
    }

    // check if a ticket id already exists
    private boolean checkTicketIdExists(int ticketId) {
        String query = "SELECT COUNT(*) FROM tickets WHERE ticket_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ticketId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // returns true if the specified seat is already occupied for the given showtime
    public boolean isSeatOccupied(int showtimeId, int seatId) {
        String query = "SELECT COUNT(*) FROM tickets WHERE showtime_id = ? AND seat_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, showtimeId);
            statement.setInt(2, seatId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int saveTicket(int showtimeId, int seatId, double price) {
        // insert a new ticket with a generated unique ticket id
        String query = "INSERT INTO tickets (ticket_id, showtime_id, seat_id, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            int ticketId = generateUniqueTicketId();
            statement.setInt(1, ticketId);
            statement.setInt(2, showtimeId);
            statement.setInt(3, seatId);
            statement.setDouble(4, price);
            statement.executeUpdate();
            return ticketId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean updateTicket(Tickets ticket) {
        String query = "UPDATE tickets SET showtime_id = ?, seat_id = ?, price = ? WHERE ticket_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ticket.getShowtimeId());
            statement.setInt(2, ticket.getSeatId());
            statement.setDouble(3, ticket.getPrice());
            statement.setInt(4, ticket.getTicketId());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteTicket(int id) {
        String query = "DELETE FROM tickets WHERE ticket_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
