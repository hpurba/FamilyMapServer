package service.request;

import model.Event;
import model.Person;
import model.User;

public class LoadRequest {

    private User[] users;
    private Person[] persons;
    private Event[] events;

    // Getters
    public User[] getUsersArray() { return users; }
    public Person[] getPersonsArray() { return persons; }
    public Event[] getEventsArray() { return events; }
}
