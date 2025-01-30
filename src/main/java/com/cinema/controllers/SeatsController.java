package com.cinema.controllers;

import com.cinema.dao.SeatsDAO;
import com.cinema.entities.Seats;

import java.util.List;

public class SeatsController {
    private final SeatsDAO seatsDAO;

    public SeatsController() {
        this.seatsDAO = new SeatsDAO();
    }

    public List<Seats> getSeatsByHallId(int hallId) {
        return seatsDAO.getSeatsByHallId(hallId);
    }
}
