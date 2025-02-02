package com.cinema.ui.admin_panel.manage_showtimes;

import com.cinema.dao.MoviesDAO;
import com.cinema.dao.HallsDAO;
import com.cinema.dao.ShowtimesDAO;
import com.cinema.entities.Movies;
import com.cinema.entities.Halls;
import com.cinema.entities.Showtimes;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

// dialog form for adding or updating showtimes
public class ShowtimeForm extends JDialog {
    private JComboBox<Movies> movieDropdown;
    private JComboBox<Halls> hallDropdown;
    private JTextField dateField;
    private JTextField timeField;
    private JButton saveButton;

    private Showtimes showtime;

    private MoviesDAO moviesDAO;
    private HallsDAO hallsDAO;
    private ShowtimesDAO showtimesDAO;

    public ShowtimeForm(JFrame parent, Showtimes showtime) {
        super(parent, "Showtime Details", true);
        this.showtime = showtime;

        // initialize DAOs
        moviesDAO = new MoviesDAO();
        hallsDAO = new HallsDAO();
        showtimesDAO = new ShowtimesDAO();

        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 2, 10, 10));

        // dropdown for movie selection
        add(new JLabel("Movie:"));
        movieDropdown = new JComboBox<>();
        loadMovies();
        add(movieDropdown);

        // dropdown for hall selection
        add(new JLabel("Hall:"));
        hallDropdown = new JComboBox<>();
        loadHalls();
        add(hallDropdown);

        // input fields for date and time
        add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        add(dateField);

        add(new JLabel("Time (HH:MM):"));
        timeField = new JTextField();
        add(timeField);

        // save button to add or update a showtime
        saveButton = new JButton(showtime == null ? "Add Showtime" : "Update Showtime");
        saveButton.addActionListener(e -> saveShowtime());
        add(new JLabel());
        add(saveButton);

        // pre-fill fields if editing an existing showtime
        if (showtime != null) {
            for (int i = 0; i < movieDropdown.getItemCount(); i++) {
                Movies m = movieDropdown.getItemAt(i);
                if (m.getMovieId() == showtime.getMovieId()) {
                    movieDropdown.setSelectedIndex(i);
                    break;
                }
            }
            for (int i = 0; i < hallDropdown.getItemCount(); i++) {
                Halls h = hallDropdown.getItemAt(i);
                if (h.getHallId() == showtime.getHallId()) {
                    hallDropdown.setSelectedIndex(i);
                    break;
                }
            }
            dateField.setText(showtime.getShowDate().toString());
            timeField.setText(showtime.getShowTime().toString());
        }

        setVisible(true);
    }

    // loads available movies into the dropdown
    private void loadMovies() {
        List<Movies> movies = moviesDAO.getAllMovies();
        movieDropdown.removeAllItems();
        for (Movies movie : movies) {
            movieDropdown.addItem(movie);
        }
    }

    // loads available halls into the dropdown
    private void loadHalls() {
        List<Halls> halls = hallsDAO.getAllHalls();
        hallDropdown.removeAllItems();
        for (Halls hall : halls) {
            hallDropdown.addItem(hall);
        }
    }

    // saves the new or updated showtime
    private void saveShowtime() {
        try {
            Movies selectedMovie = (Movies) movieDropdown.getSelectedItem();
            Halls selectedHall = (Halls) hallDropdown.getSelectedItem();
            if (selectedMovie == null || selectedHall == null) {
                JOptionPane.showMessageDialog(this, "Please select both movie and hall.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String dateText = dateField.getText().trim();
            String timeText = timeField.getText().trim();
            LocalDate date = LocalDate.parse(dateText);
            LocalTime time = LocalTime.parse(timeText);

            if (showtime == null) {
                // adding a new showtime
                Showtimes newShowtime = new Showtimes();
                newShowtime.setMovieId(selectedMovie.getMovieId());
                newShowtime.setHallId(selectedHall.getHallId());
                newShowtime.setShowDate(date);
                newShowtime.setShowTime(time);
                boolean added = showtimesDAO.addShowtime(newShowtime);
                if (added) {
                    JOptionPane.showMessageDialog(this, "Showtime added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add showtime.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // updating an existing showtime
                showtime.setMovieId(selectedMovie.getMovieId());
                showtime.setHallId(selectedHall.getHallId());
                showtime.setShowDate(date);
                showtime.setShowTime(time);
                boolean updated = showtimesDAO.updateShowtime(showtime);
                if (updated) {
                    JOptionPane.showMessageDialog(this, "Showtime updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update showtime.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            dispose();
            ((ManageShowtimesFrame)getParent()).loadShowtimes();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid date or time format. Please use YYYY-MM-DD for date and HH:MM for time.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
