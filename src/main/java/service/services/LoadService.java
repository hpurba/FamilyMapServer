package service.services;

import DAO.DataAccessException;
import DAO.EventDAO;
import DAO.PersonDAO;
import DAO.UserDAO;
import model.Event;
import model.Person;
import model.User;
import service.request.LoadRequest;
import service.response.LoadResponse;

import java.sql.SQLException;

public class LoadService {

    public LoadResponse execute(LoadRequest request) throws SQLException {

        // create and load arrays from the request
        User[] usersArray = request.getUsersArray();
        Person[] personsArray = request.getPersonsArray();
        Event[] eventsArray = request.getEventsArray();

        UserDAO user_dao = new UserDAO();
        PersonDAO person_dao = new PersonDAO();
        EventDAO event_dao = new EventDAO();

        // Users
        for (int i = 0; i < usersArray.length; i++) {
            try {
                user_dao.insert(usersArray[i]);
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        }
        // Persons
        for (int i = 0; i < personsArray.length; i++) {
            try {
                person_dao.insert(personsArray[i]);
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        }
        // Events
        for (int i = 0; i < eventsArray.length; i++) {
            try {
                event_dao.insert(eventsArray[i]);
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        }

        LoadResponse loadResponse = new LoadResponse();
        loadResponse.setMessage("Successfully added " + usersArray.length + " users, " + personsArray.length + " persons, and " + eventsArray.length + " events to the database.");
        loadResponse.isSuccess("true");
        return loadResponse;
    }
}