package service.services;

import DAO.*;
import model.*;
import service.request.RegisterRequest;
import service.response.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.SQLException;


public class ClearServiceTest {
    private Database db;
    private User bestUser;
    private Person bestPerson;
    private Event bestEvent;
    ClearService clear = new ClearService();
    ClearResponse clearResponse = new ClearResponse();
    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        bestUser = new User("hpurba", "hikaru!", "hikaru.purba@gmail.com",
                "Hikaru", "Purba", "m", "123456789");
        bestPerson = new Person("123456789", "hpurba", "Hikaru",
                "Purba", "m", "", "", "");
        bestEvent = new Event("Hiking", "superman", "superhiker",
                12f, 83.2f, "Japan", "Tokyo",
                "Biking_fast", 2020);
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void clearPass() {
        User compareUserTest = bestUser;
        Person comparePersonTest = bestPerson;
        Event compareEventTest = bestEvent;
        try {
//            Connection conn = db.openConnection();
            PersonDAO personDAO = new PersonDAO();
            personDAO.insert(bestPerson);
            UserDAO userDAO = new UserDAO();
            userDAO.insert(bestUser);
            EventDAO eventDAO = new EventDAO();
            eventDAO.insert(bestEvent);
//            db.closeConnection(true);
            clearResponse = clear.execute();
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertEquals("Clear succeeded.", clearResponse.getMessage());
    }

    @Test
    public void largerClearTest() {
        User compareUserTest = bestUser;
        Person comparePersonTest = bestPerson;
        Event compareEventTest = bestEvent;
        try {
            PersonDAO personDAO = new PersonDAO();
            personDAO.insert(bestPerson);
            UserDAO userDAO = new UserDAO();
            userDAO.insert(bestUser);
            EventDAO eventDAO = new EventDAO();
            eventDAO.insert(bestEvent);

            RegisterService registerUser = new RegisterService();
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setUserName("hpurba");
            registerRequest.setPassword("hpurba4634232");
            registerRequest.setEmail("hpurba@gmail.com");
            registerRequest.setFirstName("Hikaru");
            registerRequest.setLastName("Purba");
            registerRequest.setGender("m");
            RegisterResponse registerResponse = registerUser.execute(registerRequest);
            clearResponse = clear.execute();
            assertNotEquals("hpurba", registerResponse.getUserName());

        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertEquals("Clear succeeded.", clearResponse.getMessage());
    }

}