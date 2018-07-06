package com.codecool.queststore.backend.StudentController;

import com.codecool.queststore.backend.webControllers.studentController.StudentProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentProfileTest {
    StudentProfile studentProfile;

    /**
     * Initialize StudentProfile object for testing purposes.
     */

    @BeforeEach
    void initStudentProfile() {
        this.studentProfile = new StudentProfile();
    }

    /**
     * Test parseStudentData method from StudentProfile controller.
     * It receives testing String (formData1) and compares map returned from parseStudentData()
     * method with map (expectedData - with correctly parserd user data).
     */

    @Test
    void testParseStudentData() {
        String formData1 = "submit=Phill&value=Brzozo";
        Map<String, String> expectedData1 = new HashMap<>();
        expectedData1.put("submit", "Phill");
        expectedData1.put("value", "Brzozo");

        Map<String, String> parsedData = studentProfile.parseStudentData(formData1);

        assertEquals(expectedData1, parsedData);
    }

    /**
     * Test behavior of parseStudentData method when it receives form without any personal data.
     * It doesn't generate any exception, instead it returns empty map.
     */

    @Test
    void testParseStudentDataWithEmptyForm() {
        String formData = "submit=&value=";
        Map<String, String> expectedData = new HashMap<>();
        Map<String, String> parsedData = studentProfile.parseStudentData(formData);

        assertEquals(expectedData, parsedData);

    }
}
