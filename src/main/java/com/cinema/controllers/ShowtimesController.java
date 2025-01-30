package com.cinema.controllers;

import com.cinema.dao.ShowtimesDAO;
import com.cinema.entities.Showtimes;

import java.util.List;

public class ShowtimesController {
    private final ShowtimesDAO showtimeDAO;

    public ShowtimesController() {
        this.showtimeDAO = new ShowtimesDAO();
    }

    public List<Showtimes> getShowtimesByMovieId(int movieId) {
        return showtimeDAO.getShowtimesByMovieId(movieId);
    }
}
