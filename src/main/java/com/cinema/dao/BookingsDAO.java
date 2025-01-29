package com.cinema.dao;

import com.cinema.dao.interfaces.IBookingsDAO;
import com.cinema.db.DBConnectionManager;
import com.cinema.entities.Bookings;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingsDAO implements IBookingsDAO {

    private Connection connection;

    public BookingsDAO() {
        try {
            this.connection = DBConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Bookings> getAllBookings() {
        List<Bookings> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Bookings booking = new Bookings();
                booking.setBookingId(resultSet.getInt("booking_id"));
                booking.setUserId(resultSet.getInt("user_id"));
                booking.setShowtimeId(resultSet.getInt("showtime_id"));
                booking.setBookingDate(resultSet.getTimestamp("booking_date").toLocalDateTime());
                booking.setStatus(resultSet.getString("status"));
                bookings.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public Bookings getBookingById(int id) {
        Bookings booking = null;
        String query = "SELECT * FROM Booking WHERE booking_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                booking = new Bookings();
                booking.setBookingId(resultSet.getInt("booking_id"));
                booking.setUserId(resultSet.getInt("user_id"));
                booking.setShowtimeId(resultSet.getInt("showtime_id"));
                booking.setBookingDate(resultSet.getTimestamp("booking_date").toLocalDateTime());
                booking.setStatus(resultSet.getString("status"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }

    @Override
    public boolean addBooking(Bookings booking) {
        String query = "INSERT INTO Booking (user_id, showtime_id, booking_date, status) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, booking.getUserId());
            statement.setInt(2, booking.getShowtimeId());
            statement.setTimestamp(3, Timestamp.valueOf(booking.getBookingDate()));
            statement.setString(4, booking.getStatus());
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateBooking(Bookings booking) {
        String query = "UPDATE Booking SET user_id = ?, showtime_id = ?, booking_date = ?, status = ? WHERE booking_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, booking.getUserId());
            statement.setInt(2, booking.getShowtimeId());
            statement.setTimestamp(3, Timestamp.valueOf(booking.getBookingDate()));
            statement.setString(4, booking.getStatus());
            statement.setInt(5, booking.getBookingId());
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteBooking(int id) {
        String query = "DELETE FROM Booking WHERE booking_id = ?";

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
