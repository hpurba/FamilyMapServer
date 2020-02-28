package service.response;

import model.Person;
import java.util.*;

/**
 * Success Response Body: The response body returns a JSON object with a data attribute that
 * contains an array of Person objects. Each Person object has the same format as described in
 * previous section on the /person/[personID] API.
 * {
 * "data": [  Array of Person objects  ]
        * success:true // Boolean identifier
        *}
        *Error Response Body:
        *{
        * message:Description of the error
        * success:false // Boolean identifier
        *}
 */
public class personResponse extends Response {

    private ArrayList<Person> personsArrayList;
    private String message;

    // Initialize
    public personResponse() {
        personsArrayList = new ArrayList<Person>();
    }

    // Setters
    public void setPersonsArrayList(ArrayList<Person> personsArrayList) { this.personsArrayList = personsArrayList; }
    public void setMessage(String message) { this.message = message; }
}