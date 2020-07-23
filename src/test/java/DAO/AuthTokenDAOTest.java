package DAO;

import model.AuthorizationToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AuthTokenDAOTest {
    Database db;
    AuthorizationToken bestAuthToken;

    @BeforeEach
    public void setUp() throws Exception {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        bestAuthToken = new AuthorizationToken("ofjwi3880-0ea6-4bcc-96ce-ecge5jf649f1", "hikarupurba");
        //and make sure to initialize our tables since we don't know if our database files exist yet
        db.openConnection();
        db.closeConnection(true);
    }

    @AfterEach
    public void tearDown() throws Exception {
        //here we can get rid of anything from our tests we don't want to affect the rest of our program
        //lets clear the tables so that any data we entered for testing doesn't linger in our files
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void insertPass() throws Exception {
        AuthorizationToken compareTest = null;

        try {
            Connection conn = db.openConnection();
            AuthorizationTokenDAO authTokenDAO = new AuthorizationTokenDAO();
            authTokenDAO.addAuthorizationToken(bestAuthToken);
            compareTest = authTokenDAO.findAuthToken(bestAuthToken.getToken());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNotNull(compareTest);
        assertEquals(bestAuthToken.getToken(), compareTest.getToken());
    }

    @Test
    public void insertFail() throws Exception {
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            AuthorizationTokenDAO eDao = new AuthorizationTokenDAO();
            eDao.addAuthorizationToken(bestAuthToken);
            eDao.addAuthorizationToken(bestAuthToken);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        assertFalse(didItWork);
        AuthorizationToken compareTest = bestAuthToken;
        try {
            Connection conn = db.openConnection();
            AuthorizationTokenDAO eDao = new AuthorizationTokenDAO();
            compareTest = eDao.findAuthToken(bestAuthToken.getToken());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNull(compareTest);
    }
}