package service.services;


import DAO.DataAccessException;
import DAO.EventDAO;
import service.response.EventIDResponse;

import java.sql.SQLException;

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
public class EventIDService {

    /**
     * Executes a eventID query response
     * This API will return the single event with the specified ID.
     * The event must belong to a relative of the user associated with
     * the authorization authToken. The returned JSON contains the
     * requested event object. Authorization authToken is required.
     * @param userName
     * @param eventID
     * @return
     * @throws SQLException
     */
    public EventIDResponse execute(String userName, String eventID) throws SQLException {
        EventIDResponse response = new EventIDResponse();

        if (userName == null) {
            response.setMessage("Incorrect authorization token.");
            response.setMessage("false");
            return response;
        }

        EventDAO event_dao = new EventDAO();
        try {
            model.Event event;
            event = event_dao.find(eventID);

            if(event != null) {
                if (userName.equals(event.getUsername())) {
                    response.setEventID(event.getEventID());
                    response.setAssociatedUserName(event.getUsername());
                    response.setPersonID(event.getPersonID());
                    response.setLatitude(event.getLatitude());
                    response.setLongitude(event.getLongitude());
                    response.setCountry(event.getCountry());
                    response.setCity(event.getCity());
                    response.setEventType(event.getEventType());
                    response.setYear(event.getYear());

                    response.setMessage("success");
                    response.setSuccess("true");
                }
                else {
                    response.setMessage("event ID does not match provided authorization token.");
                    response.setSuccess("false");
                }
            }
            else {
                response.setMessage("Invalid event ID is not valid.");
                response.setSuccess("false");
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return response;
    }
}