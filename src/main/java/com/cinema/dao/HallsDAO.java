package com.cinema.dao;

import com.cinema.dao.interfaces.IHallsDAO;
import com.cinema.db.DBConnectionManager;
import com.cinema.entities.Halls;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HallsDAO implements IHallsDAO {

    private Connection connection;

    // Конструктор для подключения к базе данных
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
        String query = "SELECT * FROM Hall";

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

    @Override
    public Halls getHallById(int id) {
        Halls hall = null;
        String query = "SELECT * FROM Hall WHERE hall_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                hall = new Halls();
                hall.setHallId(resultSet.getInt("hall_id"));
                hall.setName(resultSet.getString("name"));
                hall.setCapacity(resultSet.getInt("capacity"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hall;
    }

    @Override
    public boolean addHall(Halls hall) {
        String query = "INSERT INTO Hall (name, capacity) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, hall.getName());
            statement.setInt(2, hall.getCapacity());
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateHall(Halls hall) {
        String query = "UPDATE Hall SET name = ?, capacity = ? WHERE hall_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, hall.getName());
            statement.setInt(2, hall.getCapacity());
            statement.setInt(3, hall.getHallId());
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteHall(int id) {
        String query = "DELETE FROM Hall WHERE hall_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
