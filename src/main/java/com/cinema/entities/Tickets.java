package com.cinema.entities;

public class Tickets {
    private int ticketId;
    private int showtimeId;
    private int seatId;
    private double price;
    private String status;

    public Tickets() {}

    public Tickets(int ticketId, int showtimeId, int seatId, double price, String status) {
        this.ticketId = ticketId;
        this.showtimeId = showtimeId;
        this.seatId = seatId;
        this.price = price;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Tickets{" +
                "ticketId=" + ticketId +
                ", showtimeId=" + showtimeId +
                ", seatId=" + seatId +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}
