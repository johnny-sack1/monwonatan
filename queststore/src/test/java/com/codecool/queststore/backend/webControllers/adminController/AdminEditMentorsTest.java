package com.codecool.queststore.backend.webControllers.adminController;

import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdminEditMentorsTest {
    @Test
    void test_is_admin_able_to_add_new_mentor() {

        HttpExchange mockedHttpExchange = mock(HttpExchange.class);
        URI addMentorEndpoint = URI.create("admin/classroom/add");

        when(mockedHttpExchange.getRequestURI()).thenReturn(addMentorEndpoint);
        when(mockedHttpExchange.getRequestMethod()).thenReturn("POST");

        String postString = "firstname=filip" +
                "&lastname=brzozowski" +
                "&email=fbrzozowski@fiberfuse.co.uk" +
                "&address=dupa" +
                "&classroom=1" +
                "&login=fbrzozowski" +
                "&password1=admin" +
                "&password2=admin";

        InputStream stream = new ByteArrayInputStream(postString.getBytes(StandardCharsets.UTF_8));
        when(mockedHttpExchange.getRequestBody()).thenReturn(stream);

        assertTrue((new AdminEditMentors()).submitMentorCreationPage(mockedHttpExchange));
    }

    @Test
    @Disabled
    void test_is_admin_able_to_delete_mentor() {
        HttpExchange mockedHttpExchange = mock(HttpExchange.class);
        URI addMentorEndpoint = URI.create("/admin/classroom/delete/fbrzozowski");

        when(mockedHttpExchange.getRequestURI()).thenReturn(addMentorEndpoint);
        when(mockedHttpExchange.getRequestMethod()).thenReturn("POST");

        assertTrue((new AdminEditMentors()).submitMentorDeletionPage(mockedHttpExchange));
    }
}