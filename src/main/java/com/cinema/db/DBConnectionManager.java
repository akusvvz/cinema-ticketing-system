package com.cinema.db;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
    private static DBConnectionManager instance; // singleton instance
    private Connection connection; // database connection instance

    private DBConnectionManager() throws SQLException {
        Dotenv dotenv = Dotenv.load(); // load environment variables from .env file

        // retrieve database credentials from environment variables
        String dbName = dotenv.get("DB_NAME");
        String dbUser = dotenv.get("DB_USER");
        String dbPassword = dotenv.get("DB_PASSWORD");
        String dbHost = dotenv.get("DB_HOST");
        String dbPort = dotenv.get("DB_PORT");

        // construct the database connection URL
        String url = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName + "?sslmode=require";

        try {
            Class.forName("org.postgresql.Driver"); // ensure PostgreSQL driver is loaded
            this.connection = DriverManager.getConnection(url, dbUser, dbPassword); // establish database connection
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException(e); // rethrow as SQLException if driver is not found
        }
    }

    public static DBConnectionManager getInstance() throws SQLException {
        // ensure only one instance exists and connection is not closed
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DBConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection; // return the active database connection
    }
}
