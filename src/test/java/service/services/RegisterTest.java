package service.services;

import DAO.DataAccessException;
import DAO.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.request.RegisterRequest;
import service.response.LoginResponse;
import service.response.RegisterResponse;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterTest {
    Database db;

    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        db.openConnection();
//        db.createTables();
        db.closeConnection(true);
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void registerFail() throws SQLException, DataAccessException {
        RegisterService registerUser = new RegisterService();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("hpurba");
        registerRequest.setPassword("hpurba74332323");
        registerRequest.setEmail("hpurba@gmail.com");
        registerRequest.setFirstName("Hikaru");
        registerRequest.setLastName("Purba");
        registerRequest.setGender("m");
        RegisterResponse registerResponse = registerUser.execute(registerRequest);
        registerResponse = registerUser.execute(registerRequest);
        assertEquals("error Username already exists, please choose a different one!!", registerResponse.getMessage());
    }

    @Test
    public void registerPass() throws SQLException, DataAccessException {
        RegisterService registerUser = new RegisterService();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("hpurba");
        registerRequest.setPassword("hpurba4634232");
        registerRequest.setEmail("hpurba@gmail.com");
        registerRequest.setFirstName("Hikaru");
        registerRequest.setLastName("Purba");
        registerRequest.setGender("m");
        RegisterResponse registerResponse = registerUser.execute(registerRequest);
        assertEquals("hpurba", registerResponse.getUserName());
    }
}
