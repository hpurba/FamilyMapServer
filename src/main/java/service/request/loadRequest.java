package service.request;

import model.Event;
import model.Person;
import model.User;

public class loadRequest {

    private User[] usersArray;
    private Person[] personsArray;
    private Event[] eventsArray;

    // Getters
    public User[] getUsersArray() { return usersArray; }
    public Person[] getPersonsArray() { return personsArray; }
    public Event[] getEventsArray() { return eventsArray; }
}
