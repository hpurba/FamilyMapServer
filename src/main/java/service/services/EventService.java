package service.services;

import DAO.DataAccessException;
import DAO.EventDAO;
import service.response.EventResponse;
import java.sql.SQLException;

public class EventService {

    /**
     * Executes a normal Event Response
     * This API will return ALL events for ALL family members of the current user.
     * The current user is determined from the provided authorization authToken
     * (which is required for this call). The returned JSON object contains "data"
     * which is an array of event objects. Authorization authToken is required.
     * @param userName
     * @return
     * @throws SQLException
     */
    public EventResponse execute(String userName) throws SQLException {

        EventResponse response = new EventResponse();

        EventDAO event_dao = new EventDAO();
        try {
            response = event_dao.getEvents(userName);
            if (response.getSuccess() == "false") {
                response.setMessage("error");
                response.setSuccess("false");
            }
        } catch (DataAccessException e) {
            response.setMessage("error");
            response.setSuccess("false");
            e.printStackTrace();
        }

        return response;
    }
}