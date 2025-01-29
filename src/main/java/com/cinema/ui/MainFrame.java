package com.cinema.ui;

import com.cinema.controllers.MovieController;
import com.cinema.entities.Movies;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

    public MainFrame() {
        MovieController movieController = new MovieController();

        setTitle("Cinema Ticketing System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Cinema Ticketing System", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 0));


        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 40));

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(loginButton, BorderLayout.EAST);

        JPanel moviePanel = new JPanel();
        moviePanel.setLayout(new GridLayout(5, 3, 10, 10));
        moviePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        List<Movies> movies = movieController.getAllMovies();
        for (Movies movie : movies) {
            JButton movieButton = new JButton(movie.getTitle() + " (" + movie.getRating() + ")");
            movieButton.setPreferredSize(new Dimension(200, 50));
            movieButton.addActionListener(e -> JOptionPane.showMessageDialog(this,
                    "Name: " + movie.getTitle() + "\nGenre: " + movie.getGenre() +
                            "\nDuration: " + movie.getDuration() + " min\nRating IMDb: " + movie.getRating(),
                    "Info", JOptionPane.INFORMATION_MESSAGE));
            moviePanel.add(movieButton);
        }

        add(topPanel, BorderLayout.NORTH);
        add(moviePanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
