package com.codecool.queststore.backend.webControllers.mentorController;

import com.codecool.queststore.backend.dao.QuestDAO;
import com.codecool.queststore.backend.model.Quest;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class QuestManagerTest {

    String testResponseBodyString;
    String returnedName;
    String returnedDescription;
    int returnedValue;
    Map<String, String> expectedData;
    //Creating test data for test before each test method.
    @BeforeEach
    void initTestData() {
        this.testResponseBodyString = "submit=pawel&name=brzozo&description=lol&value=3";
        this.expectedData = createMapForParse();
    }
    //Testing if method expected behaviour during UPDATE to database is preserved.
    @Test
    void testUpdateQuest() throws SQLException {
        InputStream expectedStream = new ByteArrayInputStream(testResponseBodyString.getBytes());
        HttpExchange mockedHttpExchange = mock(HttpExchange.class);
        QuestDAO mockedQuestDAO = mock(QuestDAO.class);
        QuestManager questManager = new QuestManager(mockedQuestDAO);
        ArgumentCaptor<Quest> argument = ArgumentCaptor.forClass(Quest.class);
        Quest testQuest = new Quest(1, "czastka Boga", "zjazd", 4);

        when(mockedHttpExchange.getRequestBody()).thenReturn(expectedStream);
        when(mockedQuestDAO.loadQuest(anyString())).thenReturn(testQuest);
        when(mockedHttpExchange.getResponseHeaders()).thenReturn(new Headers());

        questManager.updateQuest(mockedHttpExchange);
        
        returnedName = argument.getValue().getName();
        returnedDescription = argument.getValue().getDescription();
        returnedValue = argument.getValue().getValue();

        verify(mockedQuestDAO).updateQuest(argument.capture());
        assertEquals("brzozo", returnedName);
        assertEquals("lol", returnedDescription);
        assertEquals(3, returnedValue);

    }

    //Testing if readQuestData behaviour during execution is preserved.
    @Test
    void testReadQuestData() {
        QuestManager questManager = new QuestManager(new QuestDAO());
        InputStream targetStream = new ByteArrayInputStream(testResponseBodyString.getBytes());
        HttpExchange mockedHttpExchange = mock(HttpExchange.class);

        when(mockedHttpExchange.getRequestBody()).thenReturn(targetStream);

        Map<String, String> questData = questManager.readQuestData(mockedHttpExchange);
        assertEquals(expectedData, questData);
    }
    //Testing if parseQuestData behaviour during execution is preserved.
    @Test
    void testParseQuestData() {
        QuestManager questManager = new QuestManager(new QuestDAO());
        Map<String, String> parsedData = questManager.parseQuestData(testResponseBodyString);

        assertEquals(expectedData, parsedData);
    }

    private Map<String, String> createMapForParse() {
        Map<String, String> testMap = new HashMap<>();

        testMap.put("submit", "pawel");
        testMap.put("name", "brzozo");
        testMap.put("description", "lol");
        testMap.put("value", "3");

        return testMap;
    }
}