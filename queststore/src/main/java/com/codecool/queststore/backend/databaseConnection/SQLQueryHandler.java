package com.codecool.queststore.backend.databaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLQueryHandler {

    public static SQLQueryHandler ourInstance;
    public com.codecool.queststore.backend.databaseConnection.PostgreSQLJDBC connectionEstablisher;
    public Connection connection;

    public static SQLQueryHandler getInstance() {
        if (ourInstance == null) {
            ourInstance = new SQLQueryHandler();
        }
        return ourInstance;
    }

    private SQLQueryHandler() {
        this.connectionEstablisher = new com.codecool.queststore.backend.databaseConnection.PostgreSQLJDBC();
        this.connection = connectionEstablisher.getConnection();
    }

    public ResultSet executeQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            return null;
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
