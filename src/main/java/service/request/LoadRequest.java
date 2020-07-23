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

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
