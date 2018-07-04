package com.codecool.queststore.backend.loginManager;

import com.codecool.queststore.backend.dao.LoginDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginProcessHandlerTest {

    private static String hashedPassword;
    private static LoginDAO loginDAO;
    private static LoginProcessHandler lph;

    @BeforeEach
    void init() {
        hashedPassword = "1000:5b424037613932393232:7c5527f5d552085ab5ba1be337633649625070821585f031f71957963df8459217039609e17cec9b2508848e1244fd5770186b807ca7d8dd6cb474ccd3e045ff";
        loginDAO = mock(LoginDAO.class);
    }

    @Test
    void test_is_user_able_to_log_in_with_correct_password() throws SQLException {

        when(loginDAO.getPasswordBy("admin")).thenReturn(hashedPassword);
        when(loginDAO.getTypeBy("admin")).thenReturn("admin");
        lph = new LoginProcessHandler(loginDAO);
        String userType = lph.loginProcess("admin", "admin");
        assertEquals(userType, "admin");
    }

    @Test
    void test_user_cant_log_in_when_enter_wrong_password() throws SQLException {

        when(loginDAO.getPasswordBy("admin")).thenReturn("wrong password");
        when(loginDAO.getTypeBy("admin")).thenReturn("admin");
        lph = new LoginProcessHandler(loginDAO);

        String userType = lph.loginProcess("admin", "password with typo");
        assertEquals(userType, "invalid password");
    }
}