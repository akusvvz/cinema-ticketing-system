package com.cinema.dao.interfaces;

import com.cinema.entities.Seats;
import java.util.List;

public interface ISeatsDAO {
    Seats getSeatById(int id);
    List<Seats> getSeatsByHallId(int hallId);
}
