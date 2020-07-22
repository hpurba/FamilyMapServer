package service.response;

import com.google.gson.annotations.SerializedName;
import model.Event;

import java.util.ArrayList;

/**
 * Success Response Body: The response body returns a JSON object with a data attribute that
 * contains an array of Event objects. Each Event object has the same format as described in
 * previous section on the /event/[eventID] API.
 * {
 * data: [  Array of Event objects  ]
        * success:true // Boolean identifier
        *}
        *Error Response Body:
        *{
        * message:Description of the error
        * success:false // Boolean identifier
        *}
 */
public class EventResponse extends Response {
    private String eventID;                // Event’s unique ID (non-empty string)
    private String associatedUserName;     // Username of user account this event belongs to (non-empty string)
    private String personID;               // ID of the person this event belongs to (non-empty string)
    private double latitude;               // Latitude of the event’s location (number)
    private double longitude;             // Longitude of the event’s location (number)
    private String country;                // Name of country where event occurred (non-empty
    private String city;                   // Name of city where event occurred (non-empty string)
    private String eventType;                  // Type of event (birth, baptism, etc.) (non-empty string)
    private int year;                   // Year the event occurred (integer)

    private ArrayList<Event> data;
    private String message;

    // Initialization
    public EventResponse() { }

    public void setEvents(ArrayList<Event> eventsArrayList) { this.data = eventsArrayList; }
    public void setMessage(String message) { this.message = message; }


    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUserName() {
        return associatedUserName;
    }

    public void setAssociatedUserName(String associatedUserName) {
        this.associatedUserName = associatedUserName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ArrayList<Event> getData() {
        return data;
    }

    public void setData(ArrayList<Event> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }
}
