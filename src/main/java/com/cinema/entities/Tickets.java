package com.cinema.entities;

public class Tickets {
    private int ticketId;
    private int showtimeId;
    private int seatId;
    private double price;

    public Tickets() {}

    public Tickets(int ticketId, int showtimeId, int seatId, double price) {
        this.ticketId = ticketId;
        this.showtimeId = showtimeId;
        this.seatId = seatId;
        this.price = price;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Tickets{" +
                "ticketId=" + ticketId +
                ", showtimeId=" + showtimeId +
                ", seatId=" + seatId +
                ", price=" + price +
                '}';
    }
}
