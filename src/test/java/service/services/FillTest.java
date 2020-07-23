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
        registerRequest.setUserName("hpurba");
        registerRequest.setPassword("hpurba42322");
        registerRequest.setEmail("hpurba@gmail.com");
        registerRequest.setFirstName("Hikaru");
        registerRequest.setLastName("Purba");
        registerRequest.setGender("m");

        registerUser.execute(registerRequest);

        FillService fill = new FillService();
        FillResponse fillResponse = fill.execute("hpurba", 2);
        assertEquals("Successfully added 7 persons and 19 events to the database.", fillResponse.getMessage());
    }
}
