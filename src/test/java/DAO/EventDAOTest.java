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
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        testEvent = new Event("Hiking_007", "hpurba", "hpurba314159",
                38.8977f, 77.0365f, "United States of America", "WashingtonDC",
                "Biking_Around", 2016); // added f to convert double to float
    }

    @AfterEach
    public void tearDown() throws Exception {
        //here we can get rid of anything from our tests we don't want to affect the rest of our program
        //lets clear the tables so that any data we entered for testing doesn't linger in our files
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    // does insert and does find, this can be copied for a find pass, find fail will be different from insert fail.
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

//        //Since we know our database encountered an error, both instances of insert should have been
//        //rolled back. So for added security lets make one more quick check using our find function
//        //to make sure that our event is not in the database
//        //Set our compareTest to an actual event
//        Event compareTest = testEvent;
//        try {
//            EventDAO eDao = new EventDAO();
//            //and then get something back from our find. If the event is not in the database we
//            //should have just changed our compareTest to a null object
//            compareTest = eDao.find(testEvent.getEventID());

//        } catch (DataAccessException e) {

//        }
//
//        //Now make sure that compareTest is indeed null
//        assertNull(compareTest);
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
