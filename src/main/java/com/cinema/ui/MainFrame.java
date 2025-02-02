package com.cinema.ui;

import com.cinema.controllers.MovieController;
import com.cinema.entities.Movies;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// main window of the cinema ticketing system
public class MainFrame extends JFrame {

    public MainFrame() {
        MovieController movieController = new MovieController();

        // set up main window properties
        setTitle("Cinema Ticketing System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // create top panel with title and admin panel button
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Cinema Ticketing System", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 0));

        JButton loginButton = new JButton("Admin Panel");
        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.addActionListener(e -> new LoginFrame());

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(loginButton, BorderLayout.EAST);

        // create movie selection panel with buttons for each available movie
        JPanel moviePanel = new JPanel();
        moviePanel.setLayout(new GridLayout(5, 3, 10, 10));
        moviePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        List<Movies> movies = movieController.getAllMovies();
        for (Movies movie : movies) {
            JButton movieButton = new JButton(movie.getTitle() + " (" + movie.getRating() + ")");
            movieButton.setPreferredSize(new Dimension(200, 50));
            movieButton.addActionListener(e -> new ShowtimesFrame(movie.getMovieId(), movie.getTitle()));
            moviePanel.add(movieButton);
        }

        // add components to main frame
        add(topPanel, BorderLayout.NORTH);
        add(moviePanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // main method to launch the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
