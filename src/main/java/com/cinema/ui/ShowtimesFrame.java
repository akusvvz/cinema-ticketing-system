package com.cinema.ui;

import com.cinema.controllers.ShowtimesController;
import com.cinema.entities.Showtimes;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShowtimesFrame extends JFrame {
    private String movieTitle;
    private int movieId;

    public ShowtimesFrame(int movieId, String movieTitle) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;

        setTitle("Showtimes - " + movieTitle);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Selected Movie: " + movieTitle, SwingConstants.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 0));

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 40));

        topPanel.add(titleLabel, BorderLayout.CENTER);
        topPanel.add(loginButton, BorderLayout.EAST);

        JPanel showtimePanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(showtimePanel);
        scrollPane.setBorder(null);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
        showtimePanel.setLayout(new BoxLayout(showtimePanel, BoxLayout.Y_AXIS));

        ShowtimesController showtimeController = new ShowtimesController();
        List<Showtimes> showtimes = showtimeController.getShowtimesByMovieId(movieId);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMMM");

        Map<String, Map<String, List<Showtimes>>> groupedShowtimes = showtimes.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getShowDate().format(dateFormatter),
                        Collectors.groupingBy(Showtimes::getHallName)
                ));

        for (String date : groupedShowtimes.keySet()) {
            for (String hall : groupedShowtimes.get(date).keySet()) {
                JPanel rowPanel = new JPanel(new BorderLayout());
                JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
                labelPanel.setPreferredSize(new Dimension(200, 40));

                JLabel dateLabel = new JLabel(date);
                dateLabel.setFont(new Font("Arial", Font.BOLD, 14));

                JLabel hallLabel = new JLabel(hall);
                hallLabel.setFont(new Font("Arial", Font.PLAIN, 14));

                labelPanel.add(dateLabel);
                labelPanel.add(hallLabel);

                JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

                for (Showtimes showtime : groupedShowtimes.get(date).get(hall)) {
                    JButton timeButton = new JButton(showtime.getShowTime().format(DateTimeFormatter.ofPattern("HH:mm")));
                    timeButton.setPreferredSize(new Dimension(80, 40));

                    timeButton.addActionListener(e -> {
                        new SeatSelectionFrame(
                                movieId,
                                movieTitle,
                                date,
                                showtime.getShowTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                                hall,
                                showtime.getHallId(),
                                showtime.getShowtimeId()
                        );
                        this.dispose();
                    });

                    timePanel.add(timeButton);
                }

                rowPanel.add(labelPanel, BorderLayout.WEST);
                rowPanel.add(timePanel, BorderLayout.CENTER);
                showtimePanel.add(rowPanel);
            }
        }

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}
