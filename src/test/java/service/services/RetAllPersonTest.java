package service.services;

import DAO.DataAccessException;
import DAO.Database;
import DAO.EventDAO;
import DAO.PersonDAO;
import model.Event;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.request.RegisterRequest;
import service.response.EventResponse;
import service.response.PersonResponse;
import service.response.RegisterResponse;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RetAllPersonTest {
    Database db;

    Person[] bestPersonArray;

    private Person bestPerson1;
    private Person bestPerson2;
    private Person bestPerson3;

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
    public void RetAllPersonPass() throws SQLException {

        bestPerson1 = new Person("93472823", "immagal", "Hikaru",
                "Purba", "m", "", "", "");
        bestPerson2 = new Person("29472233", "gal1", "Imma",
                "Gal", "f", "", "", "");
        bestPerson3 = new Person("49297811", "steveo", "steve",
                "oh", "m", "", "", "");
        bestPersonArray = new Person[]{bestPerson1, bestPerson2, bestPerson3};

        PersonResponse allPersonResponse = new PersonResponse();

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
            PersonDAO pDao = new PersonDAO();

            pDao.insert(bestPerson1);
            pDao.insert(bestPerson2);
            pDao.insert(bestPerson3);

            db.closeConnection(true);


            allPersonResponse = pDao.getAllPersons(registerRequest.getUserName());

        } catch ( DataAccessException e) {
            e.printStackTrace();
            try {
                db.closeConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
        }
        int eventArraySize = allPersonResponse.getData().size();
        assertEquals(eventArraySize, 31);
    }

    @Test
    public void RetAllPersonFail() {

        bestPerson1 = new Person("93472823", "immagal", "Hikaru",
                "Purba", "m", "", "", "");
        bestPerson2 = new Person("29472233", "gal1", "Imma",
                "Gal", "f", "", "", "");
        bestPerson3 = new Person("49297811", "steveo", "steve",
                "oh", "m", "", "", "");
        bestPersonArray = new Person[]{bestPerson1, bestPerson2, bestPerson3};


        ArrayList<Person> allPersonResponse = new ArrayList<Person>();
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
            PersonDAO eDao = new PersonDAO();

            eDao.insert(bestPerson1);
            eDao.insert(bestPerson2);
            eDao.insert(bestPerson3);
            db.closeConnection(true);
            PersonResponse retAllPersons = new PersonResponse();
            allPersonResponse = retAllPersons.getData();

        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
            try {
                db.closeConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
        }
        assertEquals(null, allPersonResponse);
    }
}
