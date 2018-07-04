package com.codecool.queststore.backend.StudentController;

import com.codecool.queststore.backend.webControllers.studentController.StudentProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentProfileTest {
    StudentProfile studentProfile;

    @BeforeEach
    void initStudentProfile() {
        this.studentProfile = new StudentProfile();
    }

    @Test
    void testParseStudentData() {
        String formData = "submit=Phill&value=Brzozo";
        Map<String, String> expectedData = new HashMap<>();

        expectedData.put("submit", "Phill");
        expectedData.put("value", "Brzozo");

        Map<String, String> parsedData = studentProfile.parseStudentData(formData);

        assertEquals(expectedData, parsedData);

    }

    @Test
    void testParseStudentDataWithEmptyForm() {
        String formData = "submit=&value=";
        Map<String, String> expectedData = new HashMap<>();

        Map<String, String> parsedData = studentProfile.parseStudentData(formData);

        assertEquals(expectedData, parsedData);

    }
}
