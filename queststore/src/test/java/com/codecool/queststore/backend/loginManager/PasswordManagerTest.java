package com.codecool.queststore.backend.loginManager;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

class PasswordManagerTest {

    @Test
    void testValidatePassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        PasswordManager passwordManager = new PasswordManager();

        String password = "admin";
        String expectedHash = "1000:5b424037613932393232:7c5527f5d552085ab5ba1be337633649625070821585f031f71957963df8459217039609e17cec9b2508848e1244fd5770186b807ca7d8dd6cb474ccd3e045ff";
        assertTrue(passwordManager.validatePassword(password, expectedHash));
    }
}