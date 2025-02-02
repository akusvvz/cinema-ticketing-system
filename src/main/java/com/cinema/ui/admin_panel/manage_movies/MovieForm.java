package com.cinema.ui.admin_panel.manage_movies;

import com.cinema.dao.MoviesDAO;
import com.cinema.entities.Movies;

import javax.swing.*;
import java.awt.*;

// form for adding or editing a movie
public class MovieForm extends JDialog {
    private JTextField titleField;
    private JTextField genreField;
    private JTextField durationField;
    private JTextField ratingField;
    private JButton saveButton;

    private Movies movie;
    private MoviesDAO moviesDAO;

    public MovieForm(JFrame parent, Movies movie) {
        super(parent, "Movie Details", true);
        this.movie = movie;
        moviesDAO = new MoviesDAO();

        // set up dialog properties
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 2, 10, 10));

        // create input fields
        add(new JLabel("Title:"));
        titleField = new JTextField();
        add(titleField);

        add(new JLabel("Genre:"));
        genreField = new JTextField();
        add(genreField);

        add(new JLabel("Duration:"));
        durationField = new JTextField();
        add(durationField);

        add(new JLabel("Rating (IMDb):"));
        ratingField = new JTextField();
        add(ratingField);

        // save button to add or update a movie
        saveButton = new JButton(movie == null ? "Add Movie" : "Update Movie");
        saveButton.addActionListener(e -> saveMovie());
        add(new JLabel());
        add(saveButton);

        // populate fields if editing an existing movie
        if (movie != null) {
            titleField.setText(movie.getTitle());
            genreField.setText(movie.getGenre());
            durationField.setText(String.valueOf(movie.getDuration()));
            ratingField.setText(movie.getRating());
        }

        setVisible(true);
    }

    // saves the movie (either adds a new one or updates an existing one)
    private void saveMovie() {
        try {
            String title = titleField.getText().trim();
            String genre = genreField.getText().trim();
            int duration = Integer.parseInt(durationField.getText().trim());
            String rating = ratingField.getText().trim();

            if (title.isEmpty() || genre.isEmpty() || rating.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (movie == null) {
                // adding a new movie (ID is generated automatically)
                Movies newMovie = new Movies();
                newMovie.setTitle(title);
                newMovie.setGenre(genre);
                newMovie.setDuration(duration);
                newMovie.setRating(rating);
                boolean added = moviesDAO.addMovie(newMovie);
                if (added) {
                    JOptionPane.showMessageDialog(this, "Movie added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add movie.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // updating an existing movie
                movie.setTitle(title);
                movie.setGenre(genre);
                movie.setDuration(duration);
                movie.setRating(rating);
                boolean updated = moviesDAO.updateMovie(movie);
                if (updated) {
                    JOptionPane.showMessageDialog(this, "Movie updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update movie.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            dispose();
            ((ManageMoviesFrame) getParent()).loadMovies();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Duration must be a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
