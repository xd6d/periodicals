package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionAdapter {

    private static final String user = System.getenv("user");
    private static final String password = System.getenv("password");
    private static final String url = System.getenv("url");
    private static final String driver = System.getenv("driver");

    private static Connection connection;

    static {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ConnectionAdapter() {
    }

    public static Connection getConnection() {
        return connection;
    }
}
