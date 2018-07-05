package com.codecool.queststore.backend.databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgreSQLJDBC {

    private Connection connection;

    PostgreSQLJDBC () {
        Connection c;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/queststore",
                    "testuser", "test123");
            connection = c;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
