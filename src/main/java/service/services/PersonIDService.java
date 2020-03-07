package service.services;


import DAO.DataAccessException;
import DAO.EventDAO;
import DAO.PersonDAO;
import model.Person;
import service.response.EventIDResponse;
import service.response.PersonIDResponse;

import java.sql.SQLException;


/**
 * URL Path: /person/[personID]
 * Example: /person/7255e93e
 * Description: Returns the single Person object with the specified ID.
 * HTTP Method: GET
 * Auth Token Required: Yes
 * Request Body: None
 * Errors: Invalid auth token, Invalid personID parameter, Requested person does not belong to
 * this user, Internal server error
 */
public class PersonIDService {

    public PersonIDResponse execute(String userName, String personID) throws SQLException {
        PersonIDResponse response = new PersonIDResponse();

        if (userName == null) {
            response.setMessage("Incorrect authorization token.");
            response.setSuccess("false");
            return response;
        }

        PersonDAO person_dao = new PersonDAO();
        try {
            Person person;
            person = person_dao.find(personID);

            if(person != null) {
                if (userName.equals(person.getAssociatedUsername())) {
                    response.setPerson(person);
                    response.setSuccess("true");
                }
                else {
                    response.setMessage("person ID does not match provided authorization token.");
                    response.setSuccess("false");
                }
            }
            else {
                response.setMessage("Invalid person ID is not valid.");
                response.setSuccess("false");
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return response;
    }
}

