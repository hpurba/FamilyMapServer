package service.services;

import model.Event;
import model.Person;
import model.User;
import service.request.loadRequest;
import service.response.loadResponse;

import java.sql.SQLException;


/**
 * URL Path: /load
 * Description: Clears all data from the database (just like the /clear API), and then loads the
 * posted user, person, and event data into the database.
 * HTTP Method: POST
 * Auth Token Required: No
 * Request Body: The users property in the request body contains an array of users to be
 * created. The persons and events properties contain family history information for these
 * users. The objects contained in the persons and events arrays should be added to the
 * server’s database. The objects in the users array have the same format as those passed to
 * the /user/register API with the addition of the personID. The objects in the persons array have
 * the same format as those returned by the /person/[personID] API. The objects in the events
 * array have the same format as those returned by the /event/[eventID] API.
 * {
 * users: [  Array of User objects  ],
        7
         persons:[ / Array of Person objects / ],
         events:[ / Array of Event objects / ]
        }
        *Errors:Invalid request data(missing values,invalid values,etc.),Internal server error
 */

public class load {

    public loadResponse execute(loadRequest request) throws SQLException {

        // make DAOs

        // clear existing information (all data)

        // create and load arrays from the request
        User[] usersArray = request.getUsersArray();
        Person[] personsArray = request.getPersonsArray();
        Event[] eventsArray = request.getEventsArray();

        // add data in each array into the database

        // generate a response.

        loadResponse resp = new loadResponse();
        resp.setMessage("Successfully");
        return resp;
    }
}