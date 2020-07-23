package service.response;

import model.Event;
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
public class PersonResponse extends Response {

    private ArrayList<Person> data;
    private String message;

    // Initialization
    public PersonResponse() { }

    public void setPersons(ArrayList<Person> personArrayList) { this.data = personArrayList; }
    public void setMessage(String message) { this.message = message; }

    public ArrayList<Person> getData() {
        return data;
    }

    public void setData(ArrayList<Person> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }
}