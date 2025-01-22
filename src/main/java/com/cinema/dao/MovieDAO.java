package com.cinema.dao;

import com.cinema.dao.interfaces.IMovieDAO;
import com.cinema.db.DBConnectionManager;
import com.cinema.entities.Movies;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO implements IMovieDAO {

    private Connection connection;

    public MovieDAO() {
        try {
            this.connection = DBConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Movies> getAllMovies() {
        List<Movies> movies = new ArrayList<>();
        String query = "SELECT * FROM Movie";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Movies movie = new Movies();
                movie.setMovieId(resultSet.getInt("movie_id"));
                movie.setTitle(resultSet.getString("title"));
                movie.setGenre(resultSet.getString("genre"));
                movie.setDuration(resultSet.getInt("duration"));
                movie.setRating(resultSet.getString("rating"));
                movies.add(movie);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    @Override
    public Movies getMovieById(int id) {
        Movies movie = null;
        String query = "SELECT * FROM Movie WHERE movie_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                movie = new Movies();
                movie.setMovieId(resultSet.getInt("movie_id"));
                movie.setTitle(resultSet.getString("title"));
                movie.setGenre(resultSet.getString("genre"));
                movie.setDuration(resultSet.getInt("duration"));
                movie.setRating(resultSet.getString("rating"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movie;
    }

    @Override
    public boolean addMovie(Movies movie) {
        String query = "INSERT INTO Movie (title, genre, duration, rating) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getGenre());
            statement.setInt(3, movie.getDuration());
            statement.setString(4, movie.getRating());
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateMovie(Movies movie) {
        String query = "UPDATE Movie SET title = ?, genre = ?, duration = ?, rating = ? WHERE movie_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getGenre());
            statement.setInt(3, movie.getDuration());
            statement.setString(4, movie.getRating());
            statement.setInt(5, movie.getMovieId());
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteMovie(int id) {
        String query = "DELETE FROM Movie WHERE movie_id = ?";

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
