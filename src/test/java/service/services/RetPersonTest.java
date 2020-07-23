package service.services;

import DAO.DataAccessException;
import DAO.Database;
import DAO.EventDAO;
import DAO.PersonDAO;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.request.RegisterRequest;
import service.response.EventResponse;
import service.response.PersonIDResponse;
import service.response.PersonResponse;
import service.response.RegisterResponse;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RetPersonTest {
    Database db;
    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        db.openConnection();
        db.closeConnection(true);
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void RetPersonPass() {
        Person bestPerson = new Person("93472823", "hpurba", "Hikaru",
                "Purba", "m", "", "", "");
        Person personResponse = null;
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
            PersonDAO pDao = new PersonDAO();
            pDao.insert(bestPerson);
            String eventID = bestPerson.getPersonID();
            EventResponse retEvent = new EventResponse();
            db.closeConnection(true);



            personResponse = pDao.find("93472823");


        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
            try {
                db.closeConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
        }
        assertEquals(bestPerson.getPersonID(), personResponse.getPersonID());
        assertEquals(bestPerson.getGender(), personResponse.getGender());
        assertEquals(bestPerson.getAssociatedUsername(), personResponse.getAssociatedUsername());
    }

    @Test
    public void RetPersonFail() {
        Person bestPerson = new Person("93472823", "hpurba", "Hikaru",
                "Purba", "m", "", "", "");
        Person personResponse = null;
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
            PersonDAO pDao = new PersonDAO();
            pDao.insert(bestPerson);
            String eventID = bestPerson.getPersonID();
            EventResponse retEvent = new EventResponse();
            db.closeConnection(true);

            personResponse = pDao.find("randomPerson");
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
            try {
                db.closeConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
        }
        assertNull(personResponse);
    }
}
