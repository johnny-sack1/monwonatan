package com.codecool.queststore.backend.databaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLQueryHandler {
    //Set instance & connection field as public to make it easier to set it as a mocked object.
    //More information about mocking Singleton and its methods:
    //http://blog.cleancoder.com/uncle-bob/2015/07/01/TheLittleSingleton.html
    public static SQLQueryHandler ourInstance;
    com.codecool.queststore.backend.databaseConnection.PostgreSQLJDBC connectionEstablisher;
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
