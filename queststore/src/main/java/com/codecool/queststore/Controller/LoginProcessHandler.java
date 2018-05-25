package com.codecool.queststore.Controller;

import com.codecool.queststore.DAO.LoginDAO;

import java.sql.SQLException;

public class LoginProcessHandler {

    private LoginDAO loginDAO;

    public LoginProcessHandler() {
        this.loginDAO = new LoginDAO();
    }

    public String loginProcess(String login, String password) {
        PasswordManager passwordManager = new PasswordManager();
        try {
            String hashedPassword = loginDAO.getPasswordBy(login);
            if (passwordManager.isValidInput(password, hashedPassword)) {
                return loginDAO.getTypeBy(login);
            }
        } catch (SQLException e) {
            return "invalid password";
        }
        return null;
    }
}
