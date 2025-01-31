package com.cinema.dao.interfaces;

import com.cinema.entities.Showtimes;
import java.util.List;

public interface IShowtimesDAO {
    List<Showtimes> getAllShowtimes();
    Showtimes getShowtimeById(int id);
    List<Showtimes> getShowtimesByMovieId(int movieId);
    boolean addShowtime(Showtimes showtime);
    boolean updateShowtime(Showtimes showtime);
    boolean deleteShowtime(int id);
}
