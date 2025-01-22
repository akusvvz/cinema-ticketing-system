package com.cinema.entities;

import java.time.LocalDateTime;

public class Showtimes {
    private int showtimeId;
    private LocalDateTime dateTime;
    private int movieId;
    private int hallId;

    public Showtimes() {}

    public Showtimes(int showtimeId, LocalDateTime dateTime, int movieId, int hallId) {
        this.showtimeId = showtimeId;
        this.dateTime = dateTime;
        this.movieId = movieId;
        this.hallId = hallId;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

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

    @Override
    public String toString() {
        return "Showtimes{" +
                "showtimeId=" + showtimeId +
                ", dateTime=" + dateTime +
                ", movieId=" + movieId +
                ", hallId=" + hallId +
                '}';
    }
}
