package com.cinema.dao;

import com.cinema.dao.interfaces.ITicketsDAO;
import com.cinema.db.DBConnectionManager;
import com.cinema.entities.Tickets;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicketsDAO implements ITicketsDAO {

    private Connection connection;

    public TicketsDAO() {
        try {
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

            while (resultSet.next()) {
                Tickets ticket = new Tickets();
                ticket.setTicketId(resultSet.getInt("ticket_id"));
                ticket.setShowtimeId(resultSet.getInt("showtime_id"));
                ticket.setSeatId(resultSet.getInt("seat_id"));
                ticket.setPrice(resultSet.getDouble("price"));
                ticket.setStatus(resultSet.getString("status"));
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
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                ticket = new Tickets();
                ticket.setTicketId(resultSet.getInt("ticket_id"));
                ticket.setShowtimeId(resultSet.getInt("showtime_id"));
                ticket.setSeatId(resultSet.getInt("seat_id"));
                ticket.setPrice(resultSet.getDouble("price"));
                ticket.setStatus(resultSet.getString("status"));
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
        do {
            ticketId = 100000 + random.nextInt(900000);
            exists = checkTicketIdExists(ticketId);
        } while (exists);
        return ticketId;
    }

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

    @Override
    public int saveTicket(int showtimeId, int seatId, int price) {
        String query = "INSERT INTO tickets (ticket_id, showtime_id, seat_id, price, status) " + "VALUES (?, ?, ?, ?, 'Active')";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            int ticketId = generateUniqueTicketId();
            statement.setInt(1, ticketId);
            statement.setInt(2, showtimeId);
            statement.setInt(3, seatId);
            statement.setInt(4, price);
            statement.executeUpdate();
            return ticketId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean updateTicket(Tickets ticket) {
        String query = "UPDATE tickets SET showtime_id = ?, seat_id = ?, price = ?, status = ? " + "WHERE ticket_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ticket.getShowtimeId());
            statement.setInt(2, ticket.getSeatId());
            statement.setDouble(3, ticket.getPrice());
            statement.setString(4, ticket.getStatus());
            statement.setInt(5, ticket.getTicketId());
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
