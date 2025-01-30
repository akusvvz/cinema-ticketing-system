package com.cinema.dao;

import com.cinema.dao.interfaces.ITicketsDAO;
import com.cinema.db.DBConnectionManager;
import com.cinema.entities.Tickets;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                ticket.setBookingId(resultSet.getInt("booking_id"));
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
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                ticket = new Tickets();
                ticket.setTicketId(resultSet.getInt("ticket_id"));
                ticket.setBookingId(resultSet.getInt("booking_id"));
                ticket.setSeatId(resultSet.getInt("seat_id"));
                ticket.setPrice(resultSet.getDouble("price"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticket;
    }

    @Override
    public List<Tickets> getTicketsByBookingId(int bookingId) {
        List<Tickets> tickets = new ArrayList<>();
        String query = "SELECT * FROM tickets WHERE booking_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bookingId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Tickets ticket = new Tickets();
                ticket.setTicketId(resultSet.getInt("ticket_id"));
                ticket.setBookingId(resultSet.getInt("booking_id"));
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
    public boolean addTicket(Tickets ticket) {
        String query = "INSERT INTO tickets (booking_id, seat_id, price) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ticket.getBookingId());
            statement.setInt(2, ticket.getSeatId());
            statement.setDouble(3, ticket.getPrice());
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateTicket(Tickets ticket) {
        String query = "UPDATE tickets SET booking_id = ?, seat_id = ?, price = ? WHERE ticket_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ticket.getBookingId());
            statement.setInt(2, ticket.getSeatId());
            statement.setDouble(3, ticket.getPrice());
            statement.setInt(4, ticket.getTicketId());
            statement.executeUpdate();
            return true;

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
