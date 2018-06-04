package com.codecool.queststore.Controller;

import com.codecool.queststore.DAO.LoginDAO;
import com.codecool.queststore.PasswordManager;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public class LoginProcessHandler {

    private LoginDAO loginDAO;

    public LoginProcessHandler() {
        this.loginDAO = new LoginDAO();
    }

    public String loginProcess(String login, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PasswordManager passwordManager = new PasswordManager();
        try {
            String hashedPassword = loginDAO.getPasswordBy(login);
            if (passwordManager.validatePassword(password, hashedPassword)) {
                return loginDAO.getTypeBy(login);
            }
        } catch (SQLException e) {
            return "invalid password";
        }
        return null;
    }
}
