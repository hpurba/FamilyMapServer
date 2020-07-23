package service.services;

import DAO.Database;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.request.LoadRequest;
import service.response.LoadResponse;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LoadTest {
    private Database db;
    private Person bestPerson1;
    private Person bestPerson2;
    private Person bestPerson3;
    private Event bestEvent1;
    private Event bestEvent2;
    private Event bestEvent3;
    private User bestUser1;
    private User bestUser2;
    private User bestUser3;

    User[] bestUserArray;
    Person[] bestPersonArray;
    Event[] bestEventArray;
    Event[] wrongArray;

    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();

        bestUser1 = new User("hpurba", "supercool!", "hpurba@gmail.com",
                "Hikaru", "Purba", "m", "93472823");
        bestUser2 = new User("immagal", "immagal123", "immagal@gmail.com",
                "Imma", "Gal", "f", "29472233");
        bestUser3 = new User("steveo", "stevo1", "stevo1@gmail.com",
                "steve", "oh", "m", "49297811");
        bestUserArray = new User[]{bestUser1, bestUser2, bestUser3};

        bestPerson1 = new Person("93472823", "immagal", "Hikaru",
                "Purba", "m", "", "", "");
        bestPerson2 = new Person("29472233", "gal1", "Imma",
                "Gal", "f", "", "", "");
        bestPerson3 = new Person("49297811", "steveo", "steve",
                "oh", "m", "", "", "");
        bestPersonArray = new Person[]{bestPerson1, bestPerson2, bestPerson3};

        bestEvent1 = new Event("Biking_123A", "immagal", "29472233",
                10.3f, 10.3f, "Japan", "Tokyo",
                "Biking_Around", 2016);
        bestEvent2 = new Event("Shopping_food", "steveo", "49297811",
                10.55f, 4.3f, "USA", "cupertino",
                "Shopping", 2019);
        bestEvent3 = new Event("Sleeping22", "steveo", "49297811",
                10.55f, 4.99f, "USA", "cupertino",
                "Sleeping", 2019);
        bestEventArray = new Event[]{ bestEvent1, bestEvent2, bestEvent3};
        wrongArray = new Event[] {bestEvent1, bestEvent1, bestEvent1};

        db.openConnection();
//        db.createTables();
        db.closeConnection(true);
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
    public void loadPass() throws SQLException {
        LoadService load = new LoadService();
        LoadRequest loadRequest = new LoadRequest();
        loadRequest.setEvents(bestEventArray);
        loadRequest.setPersons(bestPersonArray);
        loadRequest.setUsers(bestUserArray);
        LoadResponse loadResponse = load.execute(loadRequest);
        assertEquals("Successfully added " + 3 + " users, " + 3 +
                " persons, and " + 3 + " events to the database.", loadResponse.getMessage());
    }

    @Test
    public void loadFail() throws SQLException {
        LoadService load = new LoadService();
        LoadRequest loadRequest = new LoadRequest();
        loadRequest.setEvents(wrongArray);
        loadRequest.setPersons(bestPersonArray);
        loadRequest.setUsers(bestUserArray);
        LoadResponse loadResponse = load.execute(loadRequest);
        assertEquals(null, loadResponse.getSuccess());

    }
}
