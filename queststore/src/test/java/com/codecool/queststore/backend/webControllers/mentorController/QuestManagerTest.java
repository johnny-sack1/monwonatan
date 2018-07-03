package com.codecool.queststore.backend.webControllers.mentorController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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