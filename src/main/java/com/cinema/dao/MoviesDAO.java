package com.cinema.dao;

import com.cinema.dao.interfaces.IMovieDAO;
import com.cinema.db.DBConnectionManager;
import com.cinema.entities.Movies;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// movies DAO: handles CRUD operations for movies
public class MoviesDAO implements IMovieDAO {

    private Connection connection;

    public MoviesDAO() {
        try {
            // get a database connection from the connection manager
            this.connection = DBConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Movies> getAllMovies() {
        List<Movies> movies = new ArrayList<>();
        String query = "SELECT * FROM movies";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            // iterate through each record and create Movies objects
            while (resultSet.next()) {
                Movies movie = new Movies();
                movie.setMovieId(resultSet.getInt("movie_id"));      // set movie id
                movie.setTitle(resultSet.getString("title"));          // set movie title
                movie.setGenre(resultSet.getString("genre"));          // set movie genre
                movie.setDuration(resultSet.getInt("duration"));       // set movie duration
                movie.setRating(resultSet.getString("rating"));        // set movie rating
                movies.add(movie);                                     // add movie to list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    @Override
    public Movies getMovieById(int id) {
        Movies movie = null;
        String query = "SELECT * FROM movies WHERE movie_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                // if a record is found, create a Movies object with its data
                if (resultSet.next()) {
                    movie = new Movies();
                    movie.setMovieId(resultSet.getInt("movie_id"));
                    movie.setTitle(resultSet.getString("title"));
                    movie.setGenre(resultSet.getString("genre"));
                    movie.setDuration(resultSet.getInt("duration"));
                    movie.setRating(resultSet.getString("rating"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movie;
    }

    @Override
    public boolean addMovie(Movies movie) {
        String query = "INSERT INTO movies (title, genre, duration, rating) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // set parameters for the insert query
            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getGenre());
            statement.setInt(3, movie.getDuration());
            statement.setString(4, movie.getRating());
            statement.executeUpdate(); // execute the insert operation
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateMovie(Movies movie) {
        String query = "UPDATE movies SET title = ?, genre = ?, duration = ?, rating = ? WHERE movie_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // update movie details in the database
            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getGenre());
            statement.setInt(3, movie.getDuration());
            statement.setString(4, movie.getRating());
            statement.setInt(5, movie.getMovieId());
            statement.executeUpdate(); // execute the update operation
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteMovie(int id) {
        String query = "DELETE FROM movies WHERE movie_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate(); // execute the delete operation
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
