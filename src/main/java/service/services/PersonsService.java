package service.services;

import DAO.DataAccessException;
import DAO.EventDAO;
import DAO.PersonDAO;
import service.response.EventResponse;
import service.response.PersonResponse;
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
public class PersonsService {

    public PersonResponse execute(String userName) throws SQLException {
        PersonResponse response = new PersonResponse();

        PersonDAO person_dao = new PersonDAO();
        try {
            response = person_dao.getAllPersons(userName);

            if (response.getSuccess() == "true") {
                response.setMessage(null);
                return response;
            }
            else {
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