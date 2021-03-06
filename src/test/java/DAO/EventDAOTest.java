package DAO;

import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class EventDAOTest {
    private Database db;
    private Event testEvent;

    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        testEvent = new Event("Hiking_007", "hpurba", "hpurba314159",
                38.8977f, 77.0365f, "United States of America", "WashingtonDC",
                "Biking_Around", 2016); // added f to convert double to float
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void insertPass() throws Exception {
        //We want to make sure insert works
        //First lets create an Event that we'll set to null. We'll use this to make sure what we put
        //in the database is actually there.
        Event compareTest = null;

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            EventDAO testEventDao = new EventDAO();
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            testEventDao.insert(testEvent);
            //So lets use a find method to get the event that we just put in back out
            compareTest = testEventDao.find(testEvent.getEventID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertEquals(testEvent, compareTest);
    }

    @Test
    public void insertFail() throws Exception {
        // NOTE: The correct way to test for an exception in Junit 5 is to use an assertThrows
        // with a lambda function. However, lambda functions are beyond the scope of this class
        // so we are doing it another way.
        boolean workedSuccessfully = true;
        try {
            EventDAO testEventDao = new EventDAO();
            testEventDao.insert(testEvent);             //if we call the method the first time it will insert it successfully
            testEventDao.insert(testEvent); //but our sql table is set up so that "eventID" must be unique. So trying to insert it again will cause the method to throw an exception
        } catch (DataAccessException e) {
            workedSuccessfully = false;
        }

        //Check to make sure that we did in fact enter our catch statement
        assertFalse(workedSuccessfully);
    }

    @Test
    public void clearPass() throws Exception {
        //We want to make sure insert works
        //First lets create an Event that we'll set to null. We'll use this to make sure what we put
        //in the database is actually there.
        Event compareTest = null;

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            EventDAO eDao = new EventDAO();
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            eDao.clear();
            //So lets use a find method to get the event that we just put in back out
            compareTest = eDao.find(testEvent.getEventID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertEquals(null, compareTest);
    }
}
