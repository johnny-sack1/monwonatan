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

    /**
     * Creates empty backpack for testing purpose;
     * @return Backpack
     * @param login of student who owns backpack
     */

    private Backpack createEmptyBackpack(String login) {
        return new Backpack(login);
    }

    /**
     * Creates User (Student instance)  for testing purpose;
     * set user ballance as 2000 coolcoins, and connect created student with empty backack.
     * @return Student
     */

    private Student createTestStudentWithSufficientBallance() {
        Student student = new Student("Phill", "Brzozo", "brzozo",
                "philip", 0, "Php master", 2000, 2000);
        Backpack emptyBackpack = new Backpack(student.getLogin());
        student.setBackpack(emptyBackpack);

        return student;
    }

    /**
     * Creates User (Student instance)  for testing purpose;
     * set user ballance as 0 coolcoins, and connect created student with empty backpack.
     * @return Student
     */

    private Student createTestStudentWithInsufficientBallance() {
        Student student = new Student("Phill", "Brzozo", "brzozo",
                "philip", 0, "Php master", 0, 2000);
        Backpack emptyBackpack = new Backpack(student.getLogin());
        student.setBackpack(emptyBackpack);

        return student;
    }

    /**
     * Creates Artifact for testing purpose;
     * sets price as 1000 coolcoins.
     * @return Artifact
     */

    private Artifact createTestArtifact() {
        Artifact artifact = new Artifact(1, true, "Test artifact",
                "Test desc", 1000);

        return artifact;
    }

    /**
     * Creates backpack with test artifact inside, for comparing purpose;
     * @return Backpack
     * @param login of student who owns backpack
     */

    private Backpack createBackpackWithTestArtifact(String login) {
        Backpack backpack = new Backpack(login);
        backpack.addToBackpack(createTestArtifact(), "unused");

        return backpack;
    }

    /**
     * Test if it's possible to buy Artifact when Student have sufficient ammount of coolcoins.
     * It is using Test User from createTestStudentWithSufficientBallance() method,
     * tries to buy Test artifact and compare if it's being add to his backpack.
     * Backpack state after transaction attempt is being captured from BackpackDAO.
     */

    @Test
    void testBuySufficientBallance() throws SQLException {
        Student testStudentBeforeBuy = createTestStudentWithSufficientBallance();
        String expectedOutput = createBackpackWithTestArtifact(testStudentBeforeBuy.getLogin()).toString();
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
        assertEquals(argument.getValue().toString(), expectedOutput);
    }

    /**
     * Test if it's possible to buy Artifact when Student have insufficient ammount of coolcoins.
     * It is using Test User from createTestStudentWithInsufficientBallance() method, and
     * tries to buy Test artifact. Test checks if balance is the same as it was before transaction
     * attempt, and also check if backpack is still empty.
     * Backpack state after transaction attempt is being captured from BackpackDAO.
     * .
     */

    @Test
    void testBuyInsufficientBallance() throws SQLException {
        Backpack emptyBackpack = createEmptyBackpack("brzozo");
        String expectedBackpack = emptyBackpack.toString();
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


        studentStore.buy(mockedHttpExchange, "1", "testArtifact");
        verify(mockedStudentDAO).updateStudent(argument.capture());
        assertEquals(argument.getValue().getCoolcoins(), 0);
        assertEquals(argument.getValue().getBackpack().toString(), expectedBackpack);
    }
}
