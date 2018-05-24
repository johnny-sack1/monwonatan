package com.codecool.queststore.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IStoreDAO {
//    ResultSet loadEntity(int id) throws SQLException;
//    ResultSet loadEntity(String name) throws SQLException;
    void createEntity(String name, String description, int value) throws SQLException;
//    void updateEntity(int id, List<String> newData) throws SQLException;
//    void updateEntity(String name, List<String> newData) throws SQLException;
}
