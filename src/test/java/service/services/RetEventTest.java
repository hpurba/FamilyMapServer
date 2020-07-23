package service.services;

import DAO.DataAccessException;
import DAO.Database;
import DAO.EventDAO;
import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.request.RegisterRequest;
import service.response.EventResponse;
import service.response.RegisterResponse;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RetEventTest {
    Database db;
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
    public void RetEventPass() {
        Event bestEvent = new Event("Biking_123A", "hpurba", "29472233",
                10.3f, 10.3f, "Japan", "Senzokuike",
                "Biking_Around", 2016);


        Event eventResponse = null;

        try {
            RegisterService registerUser = new RegisterService();
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setUserName("hpurba");
            registerRequest.setPassword("hpurba34939");
            registerRequest.setEmail("hpurba@gmail.com");
            registerRequest.setFirstName("Hikaru");
            registerRequest.setLastName("Purba");
            registerRequest.setGender("m");

            RegisterResponse registerResponse = registerUser.execute(registerRequest);
            Connection conn = db.openConnection();
            EventDAO eDao = new EventDAO();
            eDao.insert(bestEvent);
            String eventID = bestEvent.getEventID();
            EventResponse retEvent = new EventResponse();
            db.closeConnection(true);

            eventResponse = eDao.find("Biking_123A");
        } catch (DataAccessException e) {
            e.printStackTrace();
            try {
                db.closeConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(bestEvent.getEventID(), eventResponse.getEventID());
        assertEquals(bestEvent.getYear(), eventResponse.getYear());
        assertEquals(bestEvent.getEventType(), eventResponse.getEventType());
    }

    @Test
    public void retEventFail() {
        Event bestEvent = new Event("Running2123", "hpurba", "29472233",
                10.3f, 10.3f, "Japan", "Senzokuike",
                "Running_Fast", 2016);



        EventResponse eventResponse = new EventResponse();
        try {
            RegisterService registerUser = new RegisterService();
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setUserName("hpurba");
            registerRequest.setPassword("hpurba34939");
            registerRequest.setEmail("hpurba@gmail.com");
            registerRequest.setFirstName("Hikaru");
            registerRequest.setLastName("Purba");
            registerRequest.setGender("m");
            RegisterResponse registerResponse = registerUser.execute(registerRequest);
            Connection conn = db.openConnection();
            EventDAO eDao = new EventDAO();
            eDao.insert(bestEvent);
            EventResponse retEvent = new EventResponse();
            db.closeConnection(true);

            eventResponse = eDao.getEvents("Not valid ID");

        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
            try {
                db.closeConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
        }
        assertEquals("No events associated with username", eventResponse.getMessage());
    }
}
