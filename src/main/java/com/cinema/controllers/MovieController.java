package com.cinema.controllers;

import com.cinema.dao.MoviesDAO;
import com.cinema.entities.Movies;

import java.util.List;

public class MovieController {
    private final MoviesDAO moviesDAO;

    public MovieController() {
        this.moviesDAO = new MoviesDAO();
    }

    public List<Movies> getAllMovies() {
        return moviesDAO.getAllMovies();
    }
}
