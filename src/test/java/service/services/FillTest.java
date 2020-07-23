package service.services;

import DAO.DataAccessException;
import DAO.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.request.RegisterRequest;
import service.response.FillResponse;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FillTest {
    private Database db;

    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        db.openConnection();
        db.closeConnection(true);
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void FillFail() throws SQLException {
        FillService fill = new FillService();
        FillResponse fillResponse = fill.execute("Username", 2);
        assertEquals("error Username incorrect.", fillResponse.getMessage());
    }

    @Test
    public void fillPass() throws SQLException, DataAccessException {
        RegisterService registerUser = new RegisterService();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("hkang3");
        registerRequest.setPassword("hkang3123");
        registerRequest.setEmail("hkang3@gmail.com");
        registerRequest.setFirstName("Alex");
        registerRequest.setLastName("Kang");
        registerRequest.setGender("m");

        registerUser.execute(registerRequest);

        FillService fill = new FillService();
        FillResponse fillResponse = fill.execute("hkang3", 2);
        assertEquals("Successfully added 7 persons and 19 events to the database.", fillResponse.getMessage());
    }
}
