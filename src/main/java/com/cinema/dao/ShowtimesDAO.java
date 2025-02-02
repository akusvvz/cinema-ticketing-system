package com.cinema.dao;

import com.cinema.dao.interfaces.IShowtimesDAO;
import com.cinema.db.DBConnectionManager;
import com.cinema.entities.Showtimes;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// showtimes dao: handles CRUD operations for showtimes
public class ShowtimesDAO implements IShowtimesDAO {
    private Connection connection;

    public ShowtimesDAO() {
        try {
            // get database connection from the connection manager
            this.connection = DBConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Showtimes> getAllShowtimes() {
        List<Showtimes> showtimes = new ArrayList<>();
        // query to join showtimes with movies and halls for extra info
        String query = "SELECT s.*, m.title AS movie_title, h.name AS hall_name " +
                "FROM showtimes s " +
                "JOIN movies m ON s.movie_id = m.movie_id " +
                "JOIN halls h ON s.hall_id = h.hall_id";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // iterate through each record and create a Showtimes object
            while (resultSet.next()) {
                Showtimes showtime = new Showtimes();
                showtime.setShowtimeId(resultSet.getInt("showtime_id"));
                showtime.setMovieId(resultSet.getInt("movie_id"));
                showtime.setHallId(resultSet.getInt("hall_id"));
                showtime.setShowDate(resultSet.getDate("show_date").toLocalDate());
                showtime.setShowTime(resultSet.getTime("show_time").toLocalTime());
                showtime.setMovieTitle(resultSet.getString("movie_title"));
                showtime.setHallName(resultSet.getString("hall_name"));
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
        // query to retrieve a specific showtime along with movie and hall info
        String query = "SELECT s.*, m.title AS movie_title, h.name AS hall_name " +
                "FROM showtimes s " +
                "JOIN movies m ON s.movie_id = m.movie_id " +
                "JOIN halls h ON s.hall_id = h.hall_id " +
                "WHERE s.showtime_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id); // set the showtime id parameter
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                showtime = new Showtimes();
                showtime.setShowtimeId(resultSet.getInt("showtime_id"));
                showtime.setMovieId(resultSet.getInt("movie_id"));
                showtime.setHallId(resultSet.getInt("hall_id"));
                showtime.setShowDate(resultSet.getDate("show_date").toLocalDate());
                showtime.setShowTime(resultSet.getTime("show_time").toLocalTime());
                showtime.setMovieTitle(resultSet.getString("movie_title"));
                showtime.setHallName(resultSet.getString("hall_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showtime;
    }

    @Override
    public List<Showtimes> getShowtimesByMovieId(int movieId) {
        List<Showtimes> showtimes = new ArrayList<>();
        // query to get showtimes for a specific movie ordered by date, hall, and time
        String query = "SELECT s.showtime_id, s.show_date, s.show_time, s.hall_id, h.name AS hall_name " +
                "FROM showtimes s " +
                "JOIN halls h ON s.hall_id = h.hall_id " +
                "WHERE s.movie_id = ? " +
                "ORDER BY s.show_date, h.name, s.show_time";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, movieId); // set movie id parameter
            ResultSet resultSet = statement.executeQuery();

            // iterate through each record to build the list of showtimes
            while (resultSet.next()) {
                Showtimes showtime = new Showtimes();
                showtime.setShowtimeId(resultSet.getInt("showtime_id"));
                // parse the date and time from the result set
                showtime.setShowDate(LocalDate.parse(resultSet.getDate("show_date").toString()));
                showtime.setShowTime(LocalTime.parse(resultSet.getTime("show_time").toString().substring(0, 5)));
                showtime.setHallId(resultSet.getInt("hall_id"));
                showtime.setHallName(resultSet.getString("hall_name"));
                showtimes.add(showtime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showtimes;
    }

    @Override
    public boolean addShowtime(Showtimes showtime) {
        // insert a new showtime record (id is auto-generated)
        String query = "INSERT INTO showtimes (movie_id, hall_id, show_date, show_time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, showtime.getMovieId());
            statement.setInt(2, showtime.getHallId());
            statement.setDate(3, Date.valueOf(showtime.getShowDate()));
            statement.setTime(4, Time.valueOf(showtime.getShowTime()));
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateShowtime(Showtimes showtime) {
        // update an existing showtime record based on its id
        String query = "UPDATE showtimes SET show_date = ?, show_time = ?, movie_id = ?, hall_id = ? WHERE showtime_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, Date.valueOf(showtime.getShowDate()));
            statement.setTime(2, Time.valueOf(showtime.getShowTime()));
            statement.setInt(3, showtime.getMovieId());
            statement.setInt(4, showtime.getHallId());
            statement.setInt(5, showtime.getShowtimeId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteShowtime(int id) {
        // delete a showtime record by id
        String query = "DELETE FROM showtimes WHERE showtime_id = ?";
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
