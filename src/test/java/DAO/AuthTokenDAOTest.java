package DAO;

import model.AuthorizationToken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AuthTokenDAOTest {
    Database db;
    AuthorizationToken bestAuthToken;

    AuthorizationToken randomAuthToken;
    @BeforeEach
    public void setUp() throws Exception {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        bestAuthToken = new AuthorizationToken("82795880-0ea6-4bcc-96ce-ec0c28f649f1", "hpurba");
        randomAuthToken = new AuthorizationToken("adfi9wef8gaisdb", "randomdude");
        db.openConnection();
        db.closeConnection(true);
    }

    @AfterEach
    public void tearDown() throws Exception {
        //here we can get rid of anything from our tests we don't want to affect the rest of our program
        //lets clear the tables so that any data we entered for testing doesn't linger in our files
        db.openConnection();
//        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void insertPass() throws Exception {
        try {
            Connection conn = db.openConnection();
            AuthorizationTokenDAO eDao = new AuthorizationTokenDAO();
            eDao.addAuthorizationToken(bestAuthToken);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        AuthorizationToken compareTest = bestAuthToken;
        try {
            Connection conn = db.openConnection();
            AuthorizationTokenDAO eDao = new AuthorizationTokenDAO();
            compareTest = eDao.findAuthToken(bestAuthToken.getToken());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertEquals(bestAuthToken.getToken(), compareTest.getToken());
    }

    @Test
    public void findFail() throws Exception {
        try {
            Connection conn = db.openConnection();
            AuthorizationTokenDAO eDao = new AuthorizationTokenDAO();
            eDao.addAuthorizationToken(bestAuthToken);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        AuthorizationToken compareTest = bestAuthToken;
        try {
            Connection conn = db.openConnection();
            AuthorizationTokenDAO eDao = new AuthorizationTokenDAO();
            compareTest = eDao.findAuthToken(randomAuthToken.getToken());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNull(compareTest);
    }
}