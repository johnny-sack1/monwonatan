package com.codecool.queststore.backend.StudentController;

import com.codecool.queststore.backend.dao.ArtifactDAO;
import com.codecool.queststore.backend.dao.BackpackDAO;
import com.codecool.queststore.backend.dao.StudentDAO;
import com.codecool.queststore.backend.model.Artifact;
import com.codecool.queststore.backend.model.Backpack;
import com.codecool.queststore.backend.model.Student;
import com.codecool.queststore.backend.session.SessionIdContainer;
import com.codecool.queststore.backend.webControllers.studentController.StudentStore;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StudentStoreTest {
    StudentStore studentStore = new StudentStore();


    private Backpack createEmptyBackpack(String login) {
        return new Backpack(login);
    }

    private Student createTestStudentWithSufficientBallance() {
        Student student = new Student("Phill", "Brzozo", "brzozo",
                "philip", 0, "Php master", 2000, 2000);

        return student;
    }

    private Student createTestStudentWithInsufficientBallance() {
        Student student = new Student("Phill", "Brzozo", "brzozo",
                "philip", 0, "Php master", 0, 2000);

        return student;
    }

    private Artifact createTestArtifact() {
        Artifact artifact = new Artifact(1, true, "Test artifact",
                "Test desc", 1000);

        return artifact;
    }

    @Test
    void testSuccesfullTransaction() throws SQLException {
        Backpack emptyBackpack = createEmptyBackpack("brzozo");
        Student testStudentBeforeBuy = createTestStudentWithSufficientBallance();
        testStudentBeforeBuy.setBackpack(emptyBackpack);
        Artifact testArtifact = createTestArtifact();
        SessionIdContainer mockedSessionIdContainer = mock(SessionIdContainer.class);
        StudentDAO mockedStudentDAO = mock(StudentDAO.class);
        BackpackDAO mockedBackPackDAO = mock(BackpackDAO.class);
        ArtifactDAO mockedArtifactDAO = mock(ArtifactDAO.class);
        HttpExchange mockedHttpExchange = mock(HttpExchange.class);
        studentStore.setArtifactDAO(mockedArtifactDAO);
        studentStore.setStudentDAO(mockedStudentDAO);
        studentStore.setBackpackDAO(mockedBackPackDAO);
        studentStore.setSessionIdContainer(mockedSessionIdContainer);
        ArgumentCaptor<Backpack> argument = ArgumentCaptor.forClass(Backpack.class);
        when(mockedSessionIdContainer.getUserLogin("1")).thenReturn("brzozo");
        when(mockedStudentDAO.loadStudent("brzozo")).thenReturn(testStudentBeforeBuy);
        when(mockedArtifactDAO.loadArtifact("testArtifact")).thenReturn(testArtifact);

        studentStore.buy(mockedHttpExchange, "1", "testArtifact");
        verify(mockedBackPackDAO).updateBackpack(argument.capture());
        String expectedOutput = "brzozo: artifact:id:1|group:true|name:Test artifact|desc:Test desc|price:1000|status:null";
        assertEquals(argument.getValue().toString(), expectedOutput);
    }

    @Test
    void testUnsucesfullTransaction() throws SQLException {
        Backpack emptyBackpack = createEmptyBackpack("brzozo");
        Student testStudentBeforeBuy = createTestStudentWithInsufficientBallance();
        testStudentBeforeBuy.setBackpack(emptyBackpack);
        Artifact testArtifact = createTestArtifact();
        SessionIdContainer mockedSessionIdContainer = mock(SessionIdContainer.class);
        StudentDAO mockedStudentDAO = mock(StudentDAO.class);
        BackpackDAO mockedBackPackDAO = mock(BackpackDAO.class);
        ArtifactDAO mockedArtifactDAO = mock(ArtifactDAO.class);
        HttpExchange mockedHttpExchange = mock(HttpExchange.class);
        studentStore.setArtifactDAO(mockedArtifactDAO);
        studentStore.setStudentDAO(mockedStudentDAO);
        studentStore.setBackpackDAO(mockedBackPackDAO);
        studentStore.setSessionIdContainer(mockedSessionIdContainer);
        ArgumentCaptor<Student> argument = ArgumentCaptor.forClass(Student.class);
        when(mockedSessionIdContainer.getUserLogin("1")).thenReturn("brzozo");
        when(mockedStudentDAO.loadStudent("brzozo")).thenReturn(testStudentBeforeBuy);
        when(mockedArtifactDAO.loadArtifact("testArtifact")).thenReturn(testArtifact);
        String expectedBackpack = emptyBackpack.toString();

        studentStore.buy(mockedHttpExchange, "1", "testArtifact");
        verify(mockedStudentDAO).updateStudent(argument.capture());
        assertEquals(argument.getValue().getCoolcoins(), 0);
        assertEquals(argument.getValue().getBackpack().toString(), expectedBackpack);
    }
}
