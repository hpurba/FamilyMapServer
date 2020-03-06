package service.response;

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

    private ArrayList<Event> data;
    private String message;

    // Initialization
    public EventResponse() { }

    public void setEvents(ArrayList<Event> eventsArrayList) { this.data = eventsArrayList; }
    public void setMessage(String message) { this.message = message; }
}
