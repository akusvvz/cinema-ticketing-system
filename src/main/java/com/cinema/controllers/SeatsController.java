package com.cinema.controllers;

import com.cinema.dao.SeatsDAO;
import com.cinema.db.DBConnectionManager;
import com.cinema.entities.Seats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SeatsController {
    private final SeatsDAO seatsDAO;

    public SeatsController() {
        this.seatsDAO = new SeatsDAO();
    }

    public List<Seats> getSeatsByHallId(int hallId) {
        return seatsDAO.getSeatsByHallId(hallId);
    }

    // retrieves the set of occupied seat ids for a specific showtime
    public Set<Integer> getOccupiedSeatsByShowtime(int showtimeId) {
        Set<Integer> occupiedSeats = new HashSet<>();
        String query = "SELECT seat_id FROM tickets WHERE showtime_id = ?";

        try (Connection conn = DBConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // set the showtime id parameter in the query
            stmt.setInt(1, showtimeId);
            ResultSet rs = stmt.executeQuery();

            // add each occupied seat id to the set
            while (rs.next()) {
                occupiedSeats.add(rs.getInt("seat_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return occupiedSeats;
    }

}
