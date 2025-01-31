package com.cinema.ui;

import com.cinema.dao.TicketsDAO;
import com.cinema.entities.Seats;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CheckoutFrame extends JFrame {
    public CheckoutFrame(String movieTitle, String showDate, String showTime, int showtimeId, String hallName, List<Seats> selectedSeats, int totalPrice) {
        setTitle("Checkout - " + movieTitle);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        JLabel checkoutLabel = new JLabel("Checkout");
        checkoutLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(checkoutLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel movieLabel = new JLabel(movieTitle);
        movieLabel.setFont(new Font("Arial", Font.BOLD, 32));
        movieLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(movieLabel);
        mainPanel.add(Box.createVerticalStrut(15));

        JLabel ticketsLabel = new JLabel("Tickets");
        ticketsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        ticketsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(ticketsLabel);
        mainPanel.add(Box.createVerticalStrut(10));

        JPanel ticketsPanel = new JPanel(new GridLayout(selectedSeats.size(), 1, 5, 5));
        ticketsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        int count = 1;
        for (Seats seat : selectedSeats) {
            JPanel ticketPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            ticketPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            int seatPrice = seat.getSeatType().equalsIgnoreCase("VIP") ? 10 : 5;
            JLabel ticketInfo = new JLabel(
                    count + ". Row " + seat.getRowNumber() +
                            ", Seat " + seat.getSeatNumber() +
                            " (" + seat.getSeatType() + ") - $" + seatPrice
            );
            ticketInfo.setFont(new Font("Arial", Font.PLAIN, 14));
            ticketPanel.add(ticketInfo);
            ticketsPanel.add(ticketPanel);

            count++;
        }
        mainPanel.add(ticketsPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        JLabel totalLabel = new JLabel("Total: $" + totalPrice);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(totalLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        JButton payButton = new JButton("Pay");
        payButton.setPreferredSize(new Dimension(100, 40));
        payButton.addActionListener(e -> {
            TicketsDAO ticketsDAO = new TicketsDAO();
            for (Seats seat : selectedSeats) {
                int seatPrice = seat.getSeatType().equalsIgnoreCase("VIP") ? 10 : 5;
                int ticketId = ticketsDAO.saveTicket(showtimeId, seat.getSeatId(), seatPrice);
                if (ticketId != -1) {
                    new TicketFrame(ticketId, movieTitle, showDate, showTime, hallName, seat);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Error while saving a ticket",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.add(payButton);

        mainPanel.add(buttonPanel);

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}
