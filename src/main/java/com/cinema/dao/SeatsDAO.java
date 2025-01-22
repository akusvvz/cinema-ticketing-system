package com.cinema.dao;

import com.cinema.dao.interfaces.ISeatsDAO;
import com.cinema.db.DBConnectionManager;
import com.cinema.entities.Seats;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatsDAO implements ISeatsDAO {

    private Connection connection;

    public SeatsDAO() {
        try {
            this.connection = DBConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Seats> getAllSeats() {
        List<Seats> seats = new ArrayList<>();
        String query = "SELECT * FROM Seat";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Seats seat = new Seats();
                seat.setSeatId(resultSet.getInt("seat_id"));
                seat.setHallId(resultSet.getInt("hall_id"));
                seat.setRowNumber(resultSet.getInt("row_number"));
                seat.setSeatNumber(resultSet.getInt("seat_number"));
                seat.setSeatType(resultSet.getString("seat_type"));
                seats.add(seat);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    @Override
    public Seats getSeatById(int id) {
        Seats seat = null;
        String query = "SELECT * FROM Seat WHERE seat_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                seat = new Seats();
                seat.setSeatId(resultSet.getInt("seat_id"));
                seat.setHallId(resultSet.getInt("hall_id"));
                seat.setRowNumber(resultSet.getInt("row_number"));
                seat.setSeatNumber(resultSet.getInt("seat_number"));
                seat.setSeatType(resultSet.getString("seat_type"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seat;
    }

    @Override
    public List<Seats> getSeatsByHallId(int hallId) {
        List<Seats> seats = new ArrayList<>();
        String query = "SELECT * FROM Seat WHERE hall_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, hallId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Seats seat = new Seats();
                seat.setSeatId(resultSet.getInt("seat_id"));
                seat.setHallId(resultSet.getInt("hall_id"));
                seat.setRowNumber(resultSet.getInt("row_number"));
                seat.setSeatNumber(resultSet.getInt("seat_number"));
                seat.setSeatType(resultSet.getString("seat_type"));
                seats.add(seat);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    @Override
    public boolean addSeat(Seats seat) {
        String query = "INSERT INTO Seat (hall_id, row_number, seat_number, seat_type) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, seat.getHallId());
            statement.setInt(2, seat.getRowNumber());
            statement.setInt(3, seat.getSeatNumber());
            statement.setString(4, seat.getSeatType());
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateSeat(Seats seat) {
        String query = "UPDATE Seat SET hall_id = ?, row_number = ?, seat_number = ?, seat_type = ? WHERE seat_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, seat.getHallId());
            statement.setInt(2, seat.getRowNumber());
            statement.setInt(3, seat.getSeatNumber());
            statement.setString(4, seat.getSeatType());
            statement.setInt(5, seat.getSeatId());
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteSeat(int id) {
        String query = "DELETE FROM Seat WHERE seat_id = ?";

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
