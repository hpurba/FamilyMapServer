package DAO;

import DAO.DataAccessException;
import DAO.Database;
import DAO.PersonDAO;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDAOTest {
    private Database db;
    private Person bestPerson;
    private Person worstPerson;
    private Person okPerson;

    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        bestPerson = new Person("Person_123A", "hpurba", "Hikaru",
                "Purba", "m", "father_1A", "mother_1A",
                "spouse_1A");

        worstPerson = new Person("Person_321", "bob", "Bobby",
                "Dummy", "m", "father_2Z", "mother",
                "spouse_2X");
        okPerson = new Person("Person_320", "bob", "Bobby",
                "Dummy", "m", "father_2Z", "mother",
                "spouse_2X");
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void insertPass() throws Exception {
        Person compareTest = null;

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            PersonDAO eDao = new PersonDAO();
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            eDao.insert(bestPerson);
            //So lets use a find method to get the event that we just put in back out
            compareTest = eDao.find(bestPerson.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void insertFail() throws Exception {
        boolean workedSuccessfully = true;
        try {
            PersonDAO eDao = new PersonDAO();
            eDao.insert(bestPerson);
            eDao.insert(bestPerson);
        } catch (DataAccessException e) {
            workedSuccessfully = false;
        }
        assertFalse(workedSuccessfully);


    }

    @Test
    public void retrievePass() throws Exception {
        Person compareTest = null;

        try {
            Connection conn = db.openConnection();
            PersonDAO eDao = new PersonDAO();
            eDao.insert(okPerson);
            compareTest = eDao.find(okPerson.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNotNull(compareTest);
        assertEquals(okPerson, compareTest);
    }

    @Test
    public void retrieveFail() throws Exception {
        Person compareTest = null;
        try {
            Connection conn = db.openConnection();
            PersonDAO eDao = new PersonDAO();
            eDao.insert(bestPerson);
            compareTest = eDao.find(worstPerson.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }

        assertNull(compareTest);
    }

    @Test
    public void clearPass() throws Exception {
        Person compareTest = null;
        try {
            Connection conn = db.openConnection();
            PersonDAO eDao = new PersonDAO();
            eDao.clear();
            compareTest = eDao.find(bestPerson.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertEquals(null, compareTest);
    }
}
