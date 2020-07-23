package service.services;

import DAO.DataAccessException;
import DAO.Database;
import DAO.EventDAO;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.request.RegisterRequest;
import service.response.EventResponse;
import service.response.LoginResponse;
import service.response.RegisterResponse;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RetAllEventTest {
    Database db;

    Event[] bestEventArray;

    private Event bestEvent1;
    private Event bestEvent2;
    private Event bestEvent3;

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
    public void RetAllEventPass() throws SQLException {

        bestEvent1 = new Event("Biking_123A", "immagal", "29472233",
                10.3f, 10.3f, "Japan", "Saitama",
                "Biking_Around", 2016);
        bestEvent2 = new Event("Shopping_food", "steveo", "49297811",
                10.55f, 4.3f, "USA", "cupertino",
                "Shopping", 2019);
        bestEvent3 = new Event("Sleeping22", "steveo", "49297811",
                10.55f, 4.99f, "USA", "cupertino",
                "Sleeping", 2019);
        bestEventArray = new Event[]{ bestEvent1, bestEvent2, bestEvent3};

        EventResponse allEventResponse = new EventResponse();

        try {
            RegisterService registerUser = new RegisterService();
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setUserName("hpurba");
            registerRequest.setPassword("hpurba545343434");
            registerRequest.setEmail("hpurba@gmail.com");
            registerRequest.setFirstName("Hikaru");
            registerRequest.setLastName("Purba");
            registerRequest.setGender("m");

            RegisterResponse registerResponse = registerUser.execute(registerRequest);
            Connection conn = db.openConnection();
            EventDAO eDao = new EventDAO();

            eDao.insert(bestEvent1);
            eDao.insert(bestEvent2);
            eDao.insert(bestEvent3);

            db.closeConnection(true);


            allEventResponse = eDao.getEvents(registerRequest.getUserName());

        } catch ( DataAccessException e) {
            e.printStackTrace();
            try {
                db.closeConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
        }
        int eventArraySize = allEventResponse.getData().size();
        assertEquals(eventArraySize, 91);
    }

    @Test
    public void RetAllEventFail() {

        bestEvent1 = new Event("Biking_123A", "immagal", "29472233",
                10.3f, 10.3f, "Japan", "Senzokuike",
                "Biking_Around", 2016);
        bestEvent2 = new Event("Shopping_food", "steveo", "49297811",
                10.55f, 4.3f, "USA", "cupertino",
                "Shopping", 2019);
        bestEvent3 = new Event("Sleeping22", "steveo", "49297811",
                10.55f, 4.99f, "USA", "cupertino",
                "Sleeping", 2019);
        bestEventArray = new Event[]{ bestEvent1, bestEvent2, bestEvent3};


        ArrayList<Event> allEventResponse = new ArrayList<Event>();
        try {
            RegisterService registerUser = new RegisterService();
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setUserName("hpurba");
            registerRequest.setPassword("hpurba6762233");
            registerRequest.setEmail("hpurba@gmail.com");
            registerRequest.setFirstName("Hikaru");
            registerRequest.setLastName("Purba");
            registerRequest.setGender("m");
            RegisterResponse registerResponse = registerUser.execute(registerRequest);
            Connection conn = db.openConnection();
            EventDAO eDao = new EventDAO();

            eDao.insert(bestEvent1);
            eDao.insert(bestEvent2);
            eDao.insert(bestEvent3);
            db.closeConnection(true);
            EventResponse retAllEvent = new EventResponse();
            allEventResponse = retAllEvent.getData();

        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
            try {
                db.closeConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
        }
        assertEquals(null, allEventResponse);
    }
}