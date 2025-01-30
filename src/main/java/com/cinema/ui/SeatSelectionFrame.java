package com.cinema.ui;

import com.cinema.controllers.SeatsController;
import com.cinema.entities.Seats;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class SeatSelectionFrame extends JFrame {
    private int movieId;
    private String movieTitle;
    private String showDate;
    private String showTime;
    private String hallName;
    private int hallId;

    private JPanel seatPanel;
    private JLabel priceLabel;
    private Set<JToggleButton> selectedSeats = new HashSet<>();
    private int totalPrice = 0;

    public SeatSelectionFrame(int movieId, String movieTitle, String showDate, String showTime, String hallName, int hallId) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.showDate = showDate;
        this.showTime = showTime;
        this.hallName = hallName;
        this.hallId = hallId;

        setTitle("Seat Selection - " + movieTitle);
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(4, 1));
        JLabel dateLabel = new JLabel("Date: " + showDate);
        JLabel hallLabel = new JLabel("Hall: " + hallName);
        JLabel timeLabel = new JLabel("Time: " + showTime);
        priceLabel = new JLabel("Price: $0");

        dateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        hallLabel.setFont(new Font("Arial", Font.BOLD, 14));
        timeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));

        topPanel.add(dateLabel);
        topPanel.add(hallLabel);
        topPanel.add(timeLabel);
        topPanel.add(priceLabel);

        seatPanel = new JPanel(new GridLayout(10, 10, 5, 5)); // 10x10 как в макете
        loadSeats();

        JButton checkoutButton = new JButton("Proceed to check out");
        checkoutButton.setPreferredSize(new Dimension(200, 40));
        checkoutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Proceeding to checkout...", "Checkout", JOptionPane.INFORMATION_MESSAGE);
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(checkoutButton);

        add(topPanel, BorderLayout.WEST);
        add(seatPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadSeats() {
        SeatsController seatsController = new SeatsController();
        List<Seats> seats = seatsController.getSeatsByHallId(hallId);

        for (Seats seat : seats) {
            JToggleButton seatButton = new JToggleButton();
            seatButton.setPreferredSize(new Dimension(40, 40));
            seatButton.setBackground(seat.getSeatType().equalsIgnoreCase("VIP") ? Color.ORANGE : Color.LIGHT_GRAY);

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

            seatPanel.add(seatButton);
        }
    }
}
