package service.response;

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
public class eventIDResponse {
    private String associatedUserName;     // Username of user account this event belongs to (non-empty string)
    private String eventID;                // Event’s unique ID (non-empty string)
    private String personID;               // ID of the person this event belongs to (non-empty string)
    private double latitude;               // Latitude of the event’s location (number)
    private double longitude;             // Longitude of the event’s location (number)
    private String country;                // Name of country where event occurred (non-empty
    private String city;                   // Name of city where event occurred (non-empty string)
    private String birth;                  // Type of event (birth, baptism, etc.) (non-empty string)
    private String year;                   // Year the event occurred (integer)
    private boolean success;                // Boolean identifier
    private String message;

    public void setMessage(String message) { this.message = message; }
}