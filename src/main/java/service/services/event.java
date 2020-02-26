package service.services;

import service.response.eventResponse;
import java.sql.SQLException;

/**
 * URL Path: /event
 * Description: Returns ALL events for ALL family members of the current user. The current
 * user is determined from the provided auth token.
 * HTTP Method: GET
 * Auth Token Required: Yes
 * Request Body: None
 * Errors: Invalid auth token, Internal server error
 */
public class event {

    public eventResponse execute(String userName) throws SQLException {
        eventResponse response = new eventResponse();
        return response;
    }
}