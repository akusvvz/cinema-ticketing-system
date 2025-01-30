package com.cinema.entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Showtimes {
    private int showtimeId;
    private int movieId;
    private int hallId;
    private LocalDate showDate;
    private LocalTime showTime;
    private String hallName;

    public Showtimes() {}

    public Showtimes(int showtimeId, int movieId, int hallId, LocalDate showDate, LocalTime showTime) {
        this.showtimeId = showtimeId;
        this.movieId = movieId;
        this.hallId = hallId;
        this.showDate = showDate;
        this.showTime = showTime;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public LocalDate getShowDate() { return showDate; }

    public void setShowDate(LocalDate showDate) { this.showDate = showDate; }

    public LocalTime getShowTime() { return showTime; }

    public void setShowTime(LocalTime showTime) { this.showTime = showTime; }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public String getHallName() { return hallName; }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    @Override
    public String toString() {
        return "Showtimes{" +
                "showtimeId=" + showtimeId +
                ", movieId=" + movieId +
                ", hallId=" + hallId +
                ", showDate=" + showDate +
                ", showDate=" + showTime +
                '}';
    }
}
