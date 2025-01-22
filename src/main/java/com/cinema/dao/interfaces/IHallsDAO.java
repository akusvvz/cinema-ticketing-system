package com.cinema.dao.interfaces;

import com.cinema.entities.Halls;
import java.util.List;

public interface IHallsDAO {
    List<Halls> getAllHalls();
    Halls getHallById(int id);
    boolean addHall(Halls hall);
    boolean updateHall(Halls hall);
    boolean deleteHall(int id);
}
