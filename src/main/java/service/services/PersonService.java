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
public class PersonService {

    public PersonResponse execute(String userName) throws SQLException {
        PersonResponse response = new PersonResponse();

        PersonDAO person_dao = new PersonDAO();
        try {
            response = person_dao.getAllPersons(userName);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return response;
    }
}