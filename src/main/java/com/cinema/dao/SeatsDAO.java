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
            // get a database connection from the connection manager
            this.connection = DBConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Seats getSeatById(int id) {
        Seats seat = null;
        String query = "SELECT * FROM seats WHERE seat_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id); // set seat id parameter
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                seat = new Seats();
                // populate seat with data from the result set
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
        String query = "SELECT * FROM seats WHERE hall_id = ? ORDER BY row_number, seat_number";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, hallId); // set hall id parameter
            ResultSet resultSet = statement.executeQuery();

            // iterate through records and add each seat to the list
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

    // helper method to get the seat id based on hall, row, and seat number
    public int getSeatIdByRowAndNumber(int hallId, int row, int number) {
        String query = "SELECT seat_id FROM seats WHERE hall_id = ? AND row_number = ? AND seat_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, hallId);
            statement.setInt(2, row);
            statement.setInt(3, number);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("seat_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // return -1 if no seat is found
    }
}
