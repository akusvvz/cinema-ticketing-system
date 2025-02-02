package com.cinema.dao;

import com.cinema.dao.interfaces.IHallsDAO;
import com.cinema.db.DBConnectionManager;
import com.cinema.entities.Halls;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HallsDAO implements IHallsDAO {

    // connection to the database
    private Connection connection;

    public HallsDAO() {
        try {
            // get a connection instance from the connection manager
            this.connection = DBConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            // print error stack trace if connection fails
            e.printStackTrace();
        }
    }

    @Override
    public List<Halls> getAllHalls() {
        List<Halls> halls = new ArrayList<>();
        String query = "SELECT * FROM halls"; // sql query to select all halls

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // iterate through each record in the result set
            while (resultSet.next()) {
                Halls hall = new Halls();
                hall.setHallId(resultSet.getInt("hall_id"));
                hall.setName(resultSet.getString("name"));
                hall.setCapacity(resultSet.getInt("capacity"));
                halls.add(hall);
            }

        } catch (SQLException e) {
            // print error stack trace if query execution fails
            e.printStackTrace();
        }
        // return the list of halls
        return halls;
    }
}
