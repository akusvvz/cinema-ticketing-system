package com.cinema.dao;

import com.cinema.dao.interfaces.IShowtimesDAO;
import com.cinema.db.DBConnectionManager;
import com.cinema.entities.Showtimes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShowtimesDAO implements IShowtimesDAO {

    private Connection connection;

    public ShowtimesDAO() {
        try {
            this.connection = DBConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Showtimes> getAllShowtimes() {
        List<Showtimes> showtimes = new ArrayList<>();
        String query = "SELECT * FROM Showtime";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Showtimes showtime = new Showtimes();
                showtime.setShowtimeId(resultSet.getInt("showtime_id"));
                showtime.setDateTime(resultSet.getTimestamp("date_time").toLocalDateTime());
                showtime.setMovieId(resultSet.getInt("movie_id"));
                showtime.setHallId(resultSet.getInt("hall_id"));
                showtimes.add(showtime);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showtimes;
    }

    @Override
    public Showtimes getShowtimeById(int id) {
        Showtimes showtime = null;
        String query = "SELECT * FROM Showtime WHERE showtime_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                showtime = new Showtimes();
                showtime.setShowtimeId(resultSet.getInt("showtime_id"));
                showtime.setDateTime(resultSet.getTimestamp("date_time").toLocalDateTime());
                showtime.setMovieId(resultSet.getInt("movie_id"));
                showtime.setHallId(resultSet.getInt("hall_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showtime;
    }

    @Override
    public boolean addShowtime(Showtimes showtime) {
        String query = "INSERT INTO Showtime (date_time, movie_id, hall_id) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setTimestamp(1, Timestamp.valueOf(showtime.getDateTime()));
            statement.setInt(2, showtime.getMovieId());
            statement.setInt(3, showtime.getHallId());
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateShowtime(Showtimes showtime) {
        String query = "UPDATE Showtime SET date_time = ?, movie_id = ?, hall_id = ? WHERE showtime_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setTimestamp(1, Timestamp.valueOf(showtime.getDateTime()));
            statement.setInt(2, showtime.getMovieId());
            statement.setInt(3, showtime.getHallId());
            statement.setInt(4, showtime.getShowtimeId());
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteShowtime(int id) {
        String query = "DELETE FROM Showtime WHERE showtime_id = ?";

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
