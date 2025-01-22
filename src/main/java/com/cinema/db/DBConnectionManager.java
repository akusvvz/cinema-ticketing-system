package com.cinema.db;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
    private static DBConnectionManager instance;
    private Connection connection;

    private DBConnectionManager() throws SQLException {
        Dotenv dotenv = Dotenv.load();

        String dbName = dotenv.get("DB_NAME");
        String dbUser = dotenv.get("DB_USER");
        String dbPassword = dotenv.get("DB_PASSWORD");
        String dbHost = dotenv.get("DB_HOST");
        String dbPort = dotenv.get("DB_PORT");

        String url = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;

        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, dbUser, dbPassword);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
    }

    public static DBConnectionManager getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DBConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
