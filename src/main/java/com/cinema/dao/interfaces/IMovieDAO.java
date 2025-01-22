package com.cinema.dao.interfaces;

import com.cinema.entities.Movies;
import java.util.List;

public interface IMovieDAO {
    List<Movies> getAllMovies();
    Movies getMovieById(int id);
    boolean addMovie(Movies movie);
    boolean updateMovie(Movies movie);
    boolean deleteMovie(int id);
}
