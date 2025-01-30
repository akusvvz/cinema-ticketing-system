package com.cinema.dao;

import com.cinema.dao.interfaces.IShowtimesDAO;
import com.cinema.db.DBConnectionManager;
import com.cinema.entities.Showtimes;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
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
        String query = "SELECT * FROM showtimes";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Showtimes showtime = new Showtimes();
                showtime.setShowtimeId(resultSet.getInt("showtime_id"));
                showtime.setMovieId(resultSet.getInt("movie_id"));
                showtime.setHallId(resultSet.getInt("hall_id"));
                showtime.setShowDate(resultSet.getDate("show_date").toLocalDate());
                showtime.setShowTime(resultSet.getTime("show_time").toLocalTime());
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
        String query = "SELECT * FROM showtimes WHERE showtime_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                showtime = new Showtimes();
                showtime.setShowtimeId(resultSet.getInt("showtime_id"));
                showtime.setMovieId(resultSet.getInt("movie_id"));
                showtime.setHallId(resultSet.getInt("hall_id"));
                showtime.setShowDate(resultSet.getDate("show_date").toLocalDate());
                showtime.setShowTime(resultSet.getTime("show_time").toLocalTime());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showtime;
    }

    public List<Showtimes> getShowtimesByMovieId(int movieId) {
        List<Showtimes> showtimes = new ArrayList<>();
        String query = "SELECT s.show_date, s.show_time, s.hall_id, h.name AS hall_name\n" +
                "FROM showtimes s\n" +
                "JOIN halls h ON s.hall_id = h.hall_id\n" +
                "WHERE s.movie_id = ?\n" +
                "ORDER BY s.show_date, h.name, s.show_time;\n";


        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, movieId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Showtimes showtime = new Showtimes();
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
        String query = "INSERT INTO showtimes (movie_id, hall_id, show_date, show_time) VALUES (?, ?, ?)";

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
