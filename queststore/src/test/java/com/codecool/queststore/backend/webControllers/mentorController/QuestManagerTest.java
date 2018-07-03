package com.codecool.queststore.backend.webControllers.mentorController;

import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QuestManagerTest {

    QuestManager questManager;

    @BeforeEach
    void initQuestManager() {
        this.questManager = new QuestManager();
    }

    @Test
    void testUpdateQuest() {


    }

    @Test
    void testReadQuestData() {
        HttpExchange mockedHttpExchange = mock(HttpExchange.class);
        when(mockedHttpExchange.getRequestURI()).thenReturn(URI.create("http://localhost:8001//admin/classroom/add"));
        when(mockedHttpExchange.getRequestMethod()).thenReturn("POST");
        System.out.println(mockedHttpExchange.getRequestMethod());
        System.out.println(mockedHttpExchange.getRequestURI());
    }

    @Test
    void testParseQuestData() {
        String testString = "submit=pawel&value=brzozo";
        Map<String, String> expectedData = createMapForParse();

        Map<String, String> parsedData = questManager.parseQuestData(testString);

        assertEquals(expectedData, parsedData);
    }

    private Map<String, String> createMapForParse() {
        Map<String, String> testMap = new HashMap<>();

        testMap.put("submit", "pawel");
        testMap.put("value", "brzozo");

        return testMap;
    }
}