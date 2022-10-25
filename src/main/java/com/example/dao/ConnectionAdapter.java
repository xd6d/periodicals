package com.example.dao;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionAdapter{

    private static final String user = System.getenv("user");
    private static final String password = System.getenv("password");
    private static final String url = System.getenv("url");
    private static final String driver = System.getenv("driver");
    private static final Logger logger = LogManager.getLogger(ConnectionAdapter.class);

    private static Connection connection;

    static {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
    }

    private ConnectionAdapter() {
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
    }

    public static void unregisterDriver() {
        try {
            DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }
    }
}
