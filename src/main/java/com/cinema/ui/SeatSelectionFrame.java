package com.cinema.ui;

import com.cinema.controllers.SeatsController;
import com.cinema.entities.Seats;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

// window for seat selection during ticket booking
public class SeatSelectionFrame extends JFrame {
    private int movieId;
    private String movieTitle;
    private String showDate;
    private String showTime;
    private String hallName;
    private int hallId;
    private int showtimeId;

    private JPanel seatPanel;
    private JLabel priceLabel;
    private Set<JToggleButton> selectedSeats = new HashSet<>();
    private int totalPrice = 0;

    public SeatSelectionFrame(int movieId, String movieTitle, String showDate, String showTime, String hallName, int hallId, int showtimeId) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.showDate = showDate;
        this.showTime = showTime;
        this.hallName = hallName;
        this.hallId = hallId;
        this.showtimeId = showtimeId;

        // set up window properties
        setTitle("Seat Selection - " + movieTitle);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // create top panel with movie title and admin panel button
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Selected Movie: " + movieTitle, SwingConstants.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 0));

        JButton loginButton = new JButton("Admin Panel");
        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.addActionListener(e -> new LoginFrame());

        topPanel.add(titleLabel, BorderLayout.CENTER);
        topPanel.add(loginButton, BorderLayout.EAST);

        // create panel for displaying movie details and total price
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel dateLabel = new JLabel("Date: " + showDate);
        JLabel hallLabel = new JLabel("Hall: " + hallName);
        JLabel timeLabel = new JLabel("Time: " + showTime);
        priceLabel = new JLabel("Price: $0");

        dateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        hallLabel.setFont(new Font("Arial", Font.BOLD, 14));
        timeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));

        infoPanel.add(dateLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(hallLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(timeLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(priceLabel);

        // adjust seat grid size based on hall type
        if (hallName.equalsIgnoreCase("Hall VIP") || hallId == 5) {
            seatPanel = new JPanel(new GridLayout(4, 6, 5, 5));
            seatPanel.setPreferredSize(new Dimension(300, 200));
        } else {
            seatPanel = new JPanel(new GridLayout(8, 10, 5, 5));
            seatPanel.setPreferredSize(new Dimension(500, 300));
        }
        loadSeats();

        // create panel for seat selection with a screen label
        JPanel seatsWrapperPanel = new JPanel(new BorderLayout());
        JLabel screenLabel = new JLabel("Screen", SwingConstants.CENTER);
        screenLabel.setFont(new Font("Arial", Font.BOLD, 16));
        screenLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        seatsWrapperPanel.add(screenLabel, BorderLayout.NORTH);
        seatsWrapperPanel.add(seatPanel, BorderLayout.CENTER);

        // button to proceed to checkout
        JButton checkoutButton = new JButton("Proceed to check out");
        checkoutButton.setPreferredSize(new Dimension(200, 40));
        checkoutButton.addActionListener(e -> {
            List<Seats> selectedSeatList = selectedSeats.stream()
                    .map(button -> (Seats) button.getClientProperty("seat"))
                    .toList();
            new CheckoutFrame(
                    movieTitle, showDate, showTime,
                    showtimeId, hallName,
                    selectedSeatList, totalPrice
            );
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(checkoutButton);

        // add components to main frame
        add(topPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.WEST);
        add(seatsWrapperPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // load seats from the database and create interactive seat buttons
    private void loadSeats() {
        SeatsController seatsController = new SeatsController();
        List<Seats> seats = seatsController.getSeatsByHallId(hallId);

        Set<Integer> occupiedSeats = seatsController.getOccupiedSeatsByShowtime(showtimeId);

        for (Seats seat : seats) {
            JToggleButton seatButton = new JToggleButton();
            seatButton.setPreferredSize(new Dimension(30, 30));
            seatButton.putClientProperty("seat", seat);

            // mark occupied seats as unavailable
            if (occupiedSeats.contains(seat.getSeatId())) {
                seatButton.setText("❌");
                seatButton.setEnabled(false);
            } else {
                seatButton.setBackground(
                        seat.getSeatType().equalsIgnoreCase("VIP")
                                ? Color.ORANGE
                                : Color.LIGHT_GRAY
                );

                // handle seat selection and update total price
                seatButton.addActionListener(e -> {
                    if (seatButton.isSelected()) {
                        selectedSeats.add(seatButton);
                        totalPrice += seat.getSeatType().equalsIgnoreCase("VIP") ? 10 : 5;
                    } else {
                        selectedSeats.remove(seatButton);
                        totalPrice -= seat.getSeatType().equalsIgnoreCase("VIP") ? 10 : 5;
                    }
                    priceLabel.setText("Price: $" + totalPrice);
                });
            }

            seatPanel.add(seatButton);
        }
    }
}
