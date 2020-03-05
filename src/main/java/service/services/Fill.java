package service.services;

import DAO.*;
import model.Person;
import model.User;
import service.response.FillResponse;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

/**
 * URL Path: /fill/[username]/{generations}
 * Example: /fill/susan/3
 * Description: Populates the server's database with generated data for the specified user name.
 * The required "username" parameter must be a user already registered with the server. If there is
 * any data in the database already associated with the given user name, it is deleted. The
 * optional generations parameter lets the caller specify the number of generations of ancestors
 * to be generated, and must be a non-negative integer (the default is 4, which results in 31 new
 * persons each with associated events).
 * HTTP Method: POST
 * Auth Token Required: No
 * Request Body: None
 * Errors: Invalid username or generations parameter, Internal server error
 */
public class Fill {

    public FillResponse execute(String username, int generations) throws SQLException {
        System.out.println("Entered the fill!");

        FillResponse response = new FillResponse();
        boolean closingConnectionBool = false;

        Database db = new Database();
        Connection conn = null;

        try {
            conn = db.openConnection();
            int numPeople = 0;
            int numEvents = 0;

            UserDAO user_dao = new UserDAO();
            User user = user_dao.find(username);

            if(user == null) {
                response.setMessage("Username incorrect.");
                response.setSuccess(false);
            }
            else if (generations < 0) {
                response.setMessage("generations must be 1 or more.");
                response.setSuccess(false);
            }
            else {
                // If there is any data in the database associated with the given user name, it is erased.
                eraseAssociatedDataToGivenUser(user, username);

                // GENERATE THE PARENTS OF PARENTS




                // Stuck this in here for now
                response.setMessage("Successfully added " + numPeople + " persons and " + numEvents + " events to the database.");
                response.setSuccess(true);
                closingConnectionBool = true;
            }

            try { db.closeConnection(closingConnectionBool); } catch (DataAccessException e) { e.printStackTrace(); }
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }



        // get user data from username

        // delete data for specific user (if they already exist with info)

        // create 4 generations of family, or different based on number of generations
        return response;
    }

    // If there is any data in the database associated with the given user name, it is erased.
    private void eraseAssociatedDataToGivenUser(User user, String username) throws SQLException, DataAccessException {
        // Grabbing all the necessary information before doing the actual fill
        // Person
        PersonDAO person_dao = new PersonDAO();
        Person person = person_dao.find(user.getPersonID());
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        String gender = person.getGender();

        // Remove the person's Family
        person_dao.deletePersonFamily(username);    // remove persons in Persons table with associated username

        // Events
        EventDAO event_dao = new EventDAO();
        event_dao.deleteEventFamily(username);  // Delete events with associated username


        // Add the user back to the Persons table
        person = new Person(user.getPersonID(), username, firstName, lastName, gender, null, null, null); // This is needed because a reset on the father,mother, and spouse ID is needed

        // Make father and mother
        String motherID = UUID.randomUUID().toString();
        String fatherID = UUID.randomUUID().toString();

        // Set person mother and father ID
        person.setMotherID(motherID);
        person.setFatherID(fatherID);

        // Finally put the person back into the Persons table
        person_dao.insert(person);
    }















}