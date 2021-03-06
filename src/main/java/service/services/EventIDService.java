package service.services;


import DAO.DataAccessException;
import DAO.EventDAO;
import service.response.EventIDResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
            response.setMessage("error Incorrect authorization token.");
            response.setSuccess("false");
            return response;
        }

        EventDAO event_dao = new EventDAO();
        try {
            model.Event event;
            event = event_dao.find(eventID);

            if(event != null) {
                if (userName.equals(event.getUsername())) {
                    response.setEventID(event.getEventID());
                    response.setAssociatedUsername(userName);
                    response.setPersonID(event.getPersonID());

                    BigDecimal bd_latitude = new BigDecimal(event.getLatitude()).setScale(4, RoundingMode.HALF_UP);
                    BigDecimal bd_longitude = new BigDecimal(event.getLongitude()).setScale(4, RoundingMode.HALF_UP);
                    double latitude = bd_latitude.doubleValue();
                    double longitude = bd_longitude.doubleValue();

//                    response.setLatitude(event.getLatitude());
//                    response.setLongitude(event.getLongitude());
                    response.setLatitude(latitude);
                    response.setLongitude(longitude);

                    response.setCountry(event.getCountry());
                    response.setCity(event.getCity());
                    response.setEventType(event.getEventType());
                    response.setYear(event.getYear());

                    response.setMessage(null);
                    response.setSuccess("true");
                }
                else {
                    response.setMessage("error event ID does not match provided authorization token.");
                    response.setSuccess("false");
                }
            }
            else {
                response.setMessage("error Invalid event ID is not valid.");
                response.setSuccess("false");
            }
        } catch (DataAccessException e) {
            response.setMessage("error Invalid event ID is not valid.");
            response.setSuccess("false");
            e.printStackTrace();
        }

        return response;
    }


}