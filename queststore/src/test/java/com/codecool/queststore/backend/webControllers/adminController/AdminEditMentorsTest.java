package com.codecool.queststore.backend.webControllers.adminController;

import com.codecool.queststore.backend.dao.MentorDAO;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdminEditMentorsTest {

    private static MentorDAO mentorDAO;
    private static HttpExchange mockedHttpExchange;

    @BeforeEach
    void beforeEach() {
        mockedHttpExchange = mock(HttpExchange.class);
        mentorDAO = mock(MentorDAO.class);
    }

    @Test
    void test_is_admin_able_to_add_new_mentor() {

        URI addMentorEndpoint = URI.create("admin/classroom/add");

        when(mockedHttpExchange.getRequestURI()).thenReturn(addMentorEndpoint);
        when(mockedHttpExchange.getRequestMethod()).thenReturn("POST");

        String postString = "firstname=jon" +
                "&lastname=doe" +
                "&email=jon@doe.com" +
                "&address=jondoe1" +
                "&classroom=1" +
                "&login=johndoe" +
                "&password1=password" +
                "&password2=password";

        InputStream stream = new ByteArrayInputStream(postString.getBytes(StandardCharsets.UTF_8));
        when(mockedHttpExchange.getRequestBody()).thenReturn(stream);
        assertTrue((new AdminEditMentors()).submitMentorCreationPage(mockedHttpExchange, mentorDAO));
    }

    @Test
    void test_is_admin_able_to_delete_mentor() {
        URI addMentorEndpoint = URI.create("/admin/classroom/delete/test");

        when(mockedHttpExchange.getRequestURI()).thenReturn(addMentorEndpoint);
        when(mockedHttpExchange.getRequestMethod()).thenReturn("POST");
        when(mentorDAO.deleteMentor("test")).thenReturn(true);

        AdminEditMentors aem = new AdminEditMentors();
        assertTrue(aem.submitMentorDeletionPage(mockedHttpExchange, mentorDAO));
    }

    @Test
    void test_is_admin_able_to_add_new_mentor_when_passwords_do_not_match() {

        URI addMentorEndpoint = URI.create("admin/classroom/add");

        when(mockedHttpExchange.getRequestURI()).thenReturn(addMentorEndpoint);
        when(mockedHttpExchange.getRequestMethod()).thenReturn("POST");

        String postString = "firstname=jon" +
                "&lastname=doe" +
                "&email=jon@doe.com" +
                "&address=jondoe1" +
                "&classroom=1" +
                "&login=johndoe" +
                "&password1=password" +
                "&password2=oiasjdsaodij";

        InputStream stream = new ByteArrayInputStream(postString.getBytes(StandardCharsets.UTF_8));
        when(mockedHttpExchange.getRequestBody()).thenReturn(stream);
        assertFalse((new AdminEditMentors()).submitMentorCreationPage(mockedHttpExchange, mentorDAO));
    }
}