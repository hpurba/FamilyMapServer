package passoff;

import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDAO;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

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
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
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
        //here we can get rid of anything from our tests we don't want to affect the rest of our program
        //lets clear the tables so that any data we entered for testing doesn't linger in our files
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void insertPass() throws Exception {
        //We want to make sure insert works
        //First lets create an Event that we'll set to null. We'll use this to make sure what we put
        //in the database is actually there.
        User compareTest = null;

        try {
            //Let's get our connection and make a new DAO

            Connection conn = db.openConnection();
            UserDAO eDao = new UserDAO();
            //While insert returns a bool we can't use that to verify that our function actually worked
            //only that it ran without causing an error
            eDao.insert(bestUser);
            //So lets use a find method to get the event that we just put in back out
            compareTest = eDao.find(bestUser.getUserName());
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
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void insertFail() throws Exception {
        //lets do this test again but this time lets try to make it fail

        // NOTE: The correct way to test for an exception in Junit 5 is to use an assertThrows
        // with a lambda function. However, lambda functions are beyond the scope of this class
        // so we are doing it another way.
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            UserDAO eDao = new UserDAO();
            //if we call the method the first time it will insert it successfully
            eDao.insert(bestUser);
            //but our sql table is set up so that "eventID" must be unique. So trying to insert it
            //again will cause the method to throw an exception
            eDao.insert(bestUser);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            //If we catch an exception we will end up in here, where we can change our boolean to
            //false to show that our function failed to perform correctly
            db.closeConnection(false);
            didItWork = false;
        }
        //Check to make sure that we did in fact enter our catch statement
        assertFalse(didItWork);

        //Since we know our database encountered an error, both instances of insert should have been
        //rolled back. So for added security lets make one more quick check using our find function
        //to make sure that our event is not in the database
        //Set our compareTest to an actual event
        User compareTest = bestUser;
        try {
            Connection conn = db.openConnection();
            UserDAO eDao = new UserDAO();
            //and then get something back from our find. If the event is not in the database we
            //should have just changed our compareTest to a null object
            compareTest = eDao.find(bestUser.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //Now make sure that compareTest is indeed null
        assertNull(compareTest);
    }

    // RETRIEVE ------------------------------------------------------------------------------------------------------

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
//             fail();
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


    // CLEAR ------------------------------------------------------------------------------------------------------


    @Test
    public void clearPass() throws Exception {
        //We want to make sure insert works
        //First lets create an Event that we'll set to null. We'll use this to make sure what we put
        //in the database is actually there.
        User compareTest = null;

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            UserDAO eDao = new UserDAO();
            eDao.insert(mehUser);
            eDao.clear();
            //So lets use a find method to get the event that we just put in back out
            compareTest = eDao.find(bestUser.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertEquals(null, compareTest);
    }
}
