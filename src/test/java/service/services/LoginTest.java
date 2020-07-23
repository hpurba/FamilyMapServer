package service.services;

import DAO.DataAccessException;
import DAO.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.request.LoginRequest;
import service.request.RegisterRequest;
import service.response.LoginResponse;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {
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
    public void loginFail() throws SQLException, DataAccessException {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("hkang3");
        loginRequest.setPassword("hkang3123");
        LoginService login = new LoginService();

        LoginResponse loginResponse = login.execute(loginRequest);
        assertEquals("error : Username does not exist.", loginResponse.getMessage());
        RegisterService registerUser = new RegisterService();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("hkang3");
        registerRequest.setPassword("hkang3123");
        registerRequest.setEmail("hkang3@gmail.com");
        registerRequest.setFirstName("Alex");
        registerRequest.setLastName("Kang");
        registerRequest.setGender("m");
        registerUser.execute(registerRequest);
        loginRequest.setPassword("idontknow");
        loginResponse = login.execute(loginRequest);
        assertEquals("error : Password does not match.", loginResponse.getMessage());
    }

    @Test
    public void loginPass() throws SQLException, DataAccessException {
        RegisterService registerUser = new RegisterService();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("hkang3");
        registerRequest.setPassword("hkang3123");
        registerRequest.setEmail("hkang3@gmail.com");
        registerRequest.setFirstName("Alex");
        registerRequest.setLastName("Kang");
        registerRequest.setGender("m");
        registerUser.execute(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("hkang3");
        loginRequest.setPassword("hkang3123");
        LoginService login = new LoginService();
        LoginResponse loginResponse = login.execute(loginRequest);
        assertEquals("hkang3", loginResponse.getUserName());
    }
}
