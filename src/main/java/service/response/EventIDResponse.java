package service.response;

import com.google.gson.annotations.SerializedName;
import model.Event;

/**
 * Success Response Body:
 * {
 * associatedUsername:susan // Username of user account this event belongs to
 * // (non-empty string)
 * eventID:251837d7, // Event’s unique ID (non-empty string)
 * personID:7255e93e, // ID of the person this event belongs to (non-empty string)
 * latitude:65.6833, // Latitude of the event’s location (number)
 * longitude:-17.9, // Longitude of the event’s location (number)
 * country: Iceland, // Name of country where event occurred (non-empty
 * // string)
 * 9
 * city:Akureyri, // Name of city where event occurred (non-empty string)
 * eventType:birth, // Type of event (birth, baptism, etc.) (non-empty string)
 * year: 1912, // Year the event occurred (integer)
 * success:true // Boolean identifier
 * }
 * Error Response Body:
 * {
 * message:Description of the error
 * success:false // Boolean identifier
 * }
 */
public class EventIDResponse extends Response{
    private String eventID;                // Event’s unique ID (non-empty string)
    private String associatedUserName;     // Username of user account this event belongs to (non-empty string)
    private String personID;               // ID of the person this event belongs to (non-empty string)
    private double latitude;               // Latitude of the event’s location (number)
    private double longitude;             // Longitude of the event’s location (number)
    private String country;                // Name of country where event occurred (non-empty
    private String city;                   // Name of city where event occurred (non-empty string)
    private String eventType;                  // Type of event (birth, baptism, etc.) (non-empty string)
    private int year;                   // Year the event occurred (integer)

    private Event event;
    private String message;

    public void setEvent(Event event) { this.event = event; }
    public Event getEvent() { return event; }
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

    public String getMessage() {
        return message;
    }
}