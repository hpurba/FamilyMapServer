package service.services;


import model.Event;
import service.response.eventIDResponse;

/**
 * URL Path: /event/[eventID]
 * Example: /event/251837d7
 * Description: Returns the single Event object with the specified ID.
 * HTTP Method: GET
 * Auth Token Required: Yes
 * Request Body: None
 * Errors: Invalid auth token, Invalid eventID parameter, Requested event does not belong to this
 * user, Internal server error
 */
public class eventID {


    public eventIDResponse execute(Event event) {
        eventIDResponse response = new eventIDResponse();
        return response;
    }
}