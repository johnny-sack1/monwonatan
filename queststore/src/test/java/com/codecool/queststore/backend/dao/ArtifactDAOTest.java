package com.codecool.queststore.backend.dao;

import com.codecool.queststore.backend.databaseConnection.SQLQueryHandler;
import com.codecool.queststore.backend.databaseConnection.PostgreSQLJDBC;
import com.codecool.queststore.backend.model.Artifact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


//Annotations for PowerMockito
@RunWith(PowerMockRunner.class)
@PrepareForTest(SQLQueryHandler.class)
class ArtifactDAOTest {

    ArtifactDAO artifactDAO;

    @Mock
    Connection mockedConnection;
    SQLQueryHandler mockSQL;
    PreparedStatement mockedPreparedStatement;

    @Spy
    SQLQueryHandler mockedSQLQueryHandler;
    ResultSet mockedResultSet;

    //Setting up:
    //  mocks (Connection, PostgreSQLJDBC, SQLQueryHandler, PreparedStatement)
    //  spies(SQLQueryHandler.getInstance(), ResultSet)
    //  executable object (ArtifactDAO)
    @BeforeEach
    void setup() throws Exception{
        mockedConnection = mock(Connection.class);
        PostgreSQLJDBC connectionEstablisher = PowerMockito.mock(PostgreSQLJDBC.class);
        mockSQL = mock(SQLQueryHandler.class);
        SQLQueryHandler.ourInstance = mockSQL;
        PowerMockito.whenNew(PostgreSQLJDBC.class).withNoArguments().thenReturn(connectionEstablisher);
        PowerMockito.doReturn(mockedConnection).when(connectionEstablisher).getConnection();
        mockedSQLQueryHandler = PowerMockito.spy(SQLQueryHandler.getInstance());
        artifactDAO = new ArtifactDAO(mockedConnection, mockedSQLQueryHandler);
        mockedResultSet = spy(ResultSet.class);
        mockedPreparedStatement = mock(PreparedStatement.class);
    }

    //Testing if ommiting connection with database, singleton instance, returned values for ResultSet & PreparedStatement
    //execution of method loadArtifact is preserved.
    @Test
    void testIsLoadingArtifact() throws SQLException {
        Artifact testArtifact = new Artifact(1, true,
                "skryptPHP", "nvm", 300);
        String expectedQuery = "SELECT * FROM artifact WHERE artifact_id = ?";
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);

        //todo labels do zmiennych, argument value do zmiennej
        when(mockedPreparedStatement.executeQuery(anyString())).thenReturn(mockedResultSet);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
        when(mockedSQLQueryHandler.executeQuery(mockedPreparedStatement.toString())).
                                    thenReturn(mockedResultSet);
        when(mockedResultSet.getBoolean("available_for_groups")).thenReturn(true);
        when(mockedResultSet.getString("name")).thenReturn("skryptPHP");
        when(mockedResultSet.getString("description")).thenReturn("nvm");
        when(mockedResultSet.getInt("price")).thenReturn(300);

        artifactDAO.loadArtifact(1);
        String expectedName = testArtifact.getName();
        String returnedName = mockedResultSet.getString("name");
        verify(mockedConnection).prepareStatement(argument.capture());
        String queryValue = argument.getValue();

        assertEquals(expectedQuery, queryValue);
        assertEquals(expectedName, returnedName);
    }
}