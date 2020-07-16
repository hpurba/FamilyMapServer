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
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
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
        //here we can get rid of anything from our tests we don't want to affect the rest of our program
        //lets clear the tables so that any data we entered for testing doesn't linger in our files
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }


    // INSERT -----------------------------------------------------------------------------------------------------------------

    @Test
    public void insertPass() throws Exception {
        //We want to make sure insert works
        //First lets create an Event that we'll set to null. We'll use this to make sure what we put
        //in the database is actually there.
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
//             fail();
        }
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
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
        //Check to make sure that we did in fact enter our catch statement
        assertFalse(workedSuccessfully);


    }

    // RETRIEVE ------------------------------------------------------------------------------------------------------

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
//             fail();
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



    // CLEAR ------------------------------------------------------------------------------------------------------

    @Test
    public void clearPass() throws Exception {
        Person compareTest = null;
        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            PersonDAO eDao = new PersonDAO();
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            eDao.clear();
            //So lets use a find method to get the event that we just put in back out
            compareTest = eDao.find(bestPerson.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertEquals(null, compareTest);
    }
}
