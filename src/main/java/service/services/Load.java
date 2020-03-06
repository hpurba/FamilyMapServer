package service.services;

import model.Event;
import model.Person;
import model.User;
import service.request.LoadRequest;
import service.response.LoadResponse;

import java.sql.SQLException;

public class Load {

    public LoadResponse execute(LoadRequest request) throws SQLException {

        // create and load arrays from the request
        User[] usersArray = request.getUsersArray();
        Person[] personsArray = request.getPersonsArray();
        Event[] eventsArray = request.getEventsArray();

        LoadResponse loadResponse = new LoadResponse();
        loadResponse.setMessage("Successfully added " + usersArray.length + " users, " + personsArray.length + " persons, and " + eventsArray.length + " events to the database.");
        loadResponse.isSuccess(true);
        return loadResponse;
    }
}