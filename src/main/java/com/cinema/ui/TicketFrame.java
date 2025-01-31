package com.cinema.ui;

import com.cinema.entities.Seats;

import javax.swing.*;
import java.awt.*;

public class TicketFrame extends JFrame {
    public TicketFrame(int ticketId, String movieTitle, String showDate, String showTime, String hallName, Seats seat) {
        setTitle("Ticket");
        setSize(300, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Ticket", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));

        JLabel ticketIdLabel = new JLabel("№" + ticketId);
        JLabel movieLabel = new JLabel("Movie: " + movieTitle);
        JLabel dateLabel = new JLabel("Date: " + showDate);
        JLabel timeLabel = new JLabel("Time: " + showTime);
        JLabel hallLabel = new JLabel("\n\nHall: " + hallName);
        JLabel seatLabel = new JLabel("Seat: row " + seat.getRowNumber() + ", seat " + seat.getSeatNumber());
        JLabel typeLabel = new JLabel("Seat type: " + seat.getSeatType());
        JLabel priceLabel = new JLabel("Price: $" + (seat.getSeatType().equals("VIP") ? 10 : 5));

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(ticketIdLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(movieLabel);
        panel.add(dateLabel);
        panel.add(timeLabel);
        panel.add(hallLabel);
        panel.add(seatLabel);
        panel.add(typeLabel);
        panel.add(priceLabel);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }
}
