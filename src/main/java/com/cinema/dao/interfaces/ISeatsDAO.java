package com.cinema.dao.interfaces;

import com.cinema.entities.Seats;
import java.util.List;

public interface ISeatsDAO {
    List<Seats> getAllSeats();
    Seats getSeatById(int id);
    List<Seats> getSeatsByHallId(int hallId);
    boolean addSeat(Seats seat);
    boolean updateSeat(Seats seat);
    boolean deleteSeat(int id);
}
