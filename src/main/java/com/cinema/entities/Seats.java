package com.cinema.entities;

public class Seats {
    private int seatId;
    private int hallId;
    private int rowNumber;
    private int seatNumber;
    private String seatType;

    public Seats() {}

    public Seats(int seatId, int hallId, int rowNumber, int seatNumber, String seatType) {
        this.seatId = seatId;
        this.hallId = hallId;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    @Override
    public String toString() {
        return "Seats{" +
                "seatId=" + seatId +
                ", hallId=" + hallId +
                ", rowNumber=" + rowNumber +
                ", seatNumber=" + seatNumber +
                ", seatType='" + seatType + '\'' +
                '}';
    }
}
