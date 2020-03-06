package service.services;

import DAO.DataAccessException;
import DAO.EventDAO;
import DAO.PersonDAO;
import service.response.EventResponse;
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
public class Event {

    public EventResponse execute(String userName) throws SQLException {

        EventResponse response = new EventResponse();

        EventDAO event_dao = new EventDAO();
        try {
            response = event_dao.getEvents(userName);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return response;
    }
}