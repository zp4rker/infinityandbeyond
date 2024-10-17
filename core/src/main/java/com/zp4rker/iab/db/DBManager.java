package com.zp4rker.iab.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private final String connectionStr;

    public DBManager(String connectionStr) {
        this.connectionStr = connectionStr;
    }

    private Connection connection = null;
    public Connection openConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(connectionStr);
        }
        return connection;
    }
}
