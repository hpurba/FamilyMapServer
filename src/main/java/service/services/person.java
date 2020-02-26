package service.services;

import service.response.personResponse;
import java.sql.SQLException;

/**
 * URL Path: /person
 * Description: Returns ALL family members of the current user. The current user is
 * determined from the provided auth token.
 * HTTP Method: GET
 * Auth Token Required: Yes
 * Request Body: None
 * Errors: Invalid auth token, Internal server error
 */
public class person {

    public personResponse execute(String userName) throws SQLException {
        personResponse response = new personResponse();
        return response;
    }
}