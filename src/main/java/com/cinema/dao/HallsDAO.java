package com.cinema.dao;

import com.cinema.dao.interfaces.IHallsDAO;
import com.cinema.db.DBConnectionManager;
import com.cinema.entities.Halls;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HallsDAO implements IHallsDAO {

    private Connection connection;

    public HallsDAO() {
        try {
            this.connection = DBConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Halls> getAllHalls() {
        List<Halls> halls = new ArrayList<>();
        String query = "SELECT * FROM halls";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Halls hall = new Halls();
                hall.setHallId(resultSet.getInt("hall_id"));
                hall.setName(resultSet.getString("name"));
                hall.setCapacity(resultSet.getInt("capacity"));
                halls.add(hall);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return halls;
    }
}
