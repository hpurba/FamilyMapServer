package service.services;


import DAO.DataAccessException;
import DAO.PersonDAO;
import model.Person;
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

                    // put the setting in here
                    response.setPersonID(personID);
                    response.setAssociatedUsername(person.getAssociatedUsername());
                    response.setFirstName(person.getFirstName());
                    response.setLastName(person.getLastName());
                    response.setGender(person.getGender());
                    response.setFatherID(person.getFatherID());
                    response.setMotherID(person.getMotherID());
                    response.setSpouse(person.getSpouseID());
                    response.setMessage("success");
                    response.setSuccess("true");
//                    return response;
                }
                else {
                    response.setMessage("error person ID does not match provided username.");
                    response.setSuccess("false");
                }
            }
            else {
                response.setMessage("error  Invalid person ID is not valid.");
                response.setSuccess("false");
            }
        } catch (DataAccessException e) {
            response.setMessage("error ");
            response.setSuccess("false");
            e.printStackTrace();
        }
        return response;
    }
}

