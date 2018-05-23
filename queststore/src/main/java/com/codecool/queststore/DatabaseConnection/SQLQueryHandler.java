package com.codecool.queststore.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLQueryHandler {

    private static SQLQueryHandler ourInstance;
    private com.codecool.queststore.DatabaseConnection.PostgreSQLJDBC connectionEstablisher;
    private Connection connection;

    public static SQLQueryHandler getInstance() {
        if (ourInstance == null) {
            ourInstance = new SQLQueryHandler();
        }
        return ourInstance;
    }

    private SQLQueryHandler() {
        this.connectionEstablisher = new com.codecool.queststore.DatabaseConnection.PostgreSQLJDBC();
        this.connection = connectionEstablisher.getConnection();
    }

    public ResultSet executeQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query.trim());
        } catch (SQLException e) {
            return null;
        }
    }
}
