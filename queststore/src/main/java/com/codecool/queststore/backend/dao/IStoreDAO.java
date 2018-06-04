package com.codecool.queststore.backend.dao;

import java.sql.SQLException;

public interface IStoreDAO {
//    ResultSet loadEntity(int id) throws SQLException;
//    ResultSet loadEntity(String name) throws SQLException;
    void createEntity(String name, String description, int value) throws SQLException;
//    void updateEntity(int id, List<String> newData) throws SQLException;
//    void updateEntity(String name, List<String> newData) throws SQLException;
}
