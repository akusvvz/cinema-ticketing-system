package com.cinema.ui.admin_panel.manage_movies;

import com.cinema.dao.MoviesDAO;
import com.cinema.entities.Movies;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageMoviesFrame extends JFrame {
    private JTable moviesTable;
    private DefaultTableModel tableModel;
    private MoviesDAO moviesDAO;

    public ManageMoviesFrame() {
        setTitle("Manage Movies");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Manage Movies");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Title", "Genre", "Duration", "Rating (IMDb)"};
        tableModel = new DefaultTableModel(columnNames, 0);
        moviesTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(moviesTable);
        add(scrollPane, BorderLayout.CENTER);

        moviesDAO = new MoviesDAO();
        loadMovies();

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Movie");
        JButton editButton = new JButton("✎");
        JButton deleteButton = new JButton("❌");

        addButton.addActionListener(e -> openMovieForm(null));
        editButton.addActionListener(e -> editSelectedMovie());
        deleteButton.addActionListener(e -> deleteSelectedMovie());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void loadMovies() {
        List<Movies> movies = moviesDAO.getAllMovies();
        tableModel.setRowCount(0);
        for (Movies movie : movies) {
            tableModel.addRow(new Object[]{
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getGenre(),
                    movie.getDuration(),
                    movie.getRating()
            });
        }
    }

    private void openMovieForm(Movies movie) {
        new MovieForm(this, movie);
    }

    private void editSelectedMovie() {
        int selectedRow = moviesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a movie to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int movieId = (int) tableModel.getValueAt(selectedRow, 0);
        Movies movie = moviesDAO.getMovieById(movieId);
        openMovieForm(movie);
    }

    private void deleteSelectedMovie() {
        int selectedRow = moviesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a movie to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int movieId = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this movie?", "Delete Movie", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean deleted = moviesDAO.deleteMovie(movieId);
            if (deleted) {
                JOptionPane.showMessageDialog(this, "Movie deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadMovies();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete movie.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
