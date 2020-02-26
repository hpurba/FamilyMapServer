package service.services;


import model.Person;
import service.response.personIDResponse;


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
public class personID {


    public personIDResponse execute(Person person) {

        personIDResponse response = new personIDResponse();
        return response;
    }
}

