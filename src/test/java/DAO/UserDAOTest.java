package DAO;

import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDAO;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserDAOTest {
    private Database db;
    private String userName;    // User name, Non-empty String
    private String password;    // User's password, Non-empty String
    private String email;       // User's email address, Non-empty String
    private String firstName;   // User's first name, Non-empty String
    private String lastName;    // User's last name, Non-empty String
    private String gender;      // String: "f" or "m", should I make this char instead?
    private String personID;    // Unique person ID assigned to this user's generated model.Person object

    private User bestUser;
    private User mehUser;
    private User evilUser;

    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
        //and a new event with random data
        bestUser = new User("Person_123A", "password", "email",
                "Hikaru", "Purba", "m", "prba");
        mehUser = new User("googleee", "123password", "googleee@gmail.com",
                "Goo", "Gllleeee", "f", "guglE");
        evilUser = new User("mehhhhh", "123MEHHH", "meh@gmail.com",
                "Me", "hhhh", "m", "mehmeh");
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void insertPass() throws Exception {
        User compareTest = null;

        try {
            Connection conn = db.openConnection();
            UserDAO eDao = new UserDAO();
            eDao.insert(bestUser);
            compareTest = eDao.find(bestUser.getUserName());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void insertFail() throws Exception {
        boolean workedSuccessfully = true;

        try {
            UserDAO eDao = new UserDAO();
            eDao.insert(bestUser);
            eDao.insert(bestUser);
        } catch (DataAccessException e) {
            workedSuccessfully = false;
        }
        assertFalse(workedSuccessfully);
    }

    @Test
    public void retrievePass() throws Exception {
        User compareTest = null;

        try {
            Connection conn = db.openConnection();
            UserDAO eDao = new UserDAO();
            eDao.insert(mehUser);
            compareTest = eDao.find(mehUser.getUserName());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNotNull(compareTest);
        assertEquals(mehUser, compareTest);
    }

    @Test
    public void retrieveFail() throws Exception {
        User compareTest = null;
        try {
            Connection conn = db.openConnection();
            UserDAO eDao = new UserDAO();
            eDao.insert(mehUser);
            compareTest = eDao.find(evilUser.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNull(compareTest);
    }

    @Test
    public void clearPass() throws Exception {
        User compareTest = null;

        try {
            Connection conn = db.openConnection();
            UserDAO eDao = new UserDAO();
            eDao.insert(mehUser);
            eDao.clear();
            compareTest = eDao.find(bestUser.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertEquals(null, compareTest);
    }
}
