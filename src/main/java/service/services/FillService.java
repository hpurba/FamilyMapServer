package service.services;

import DAO.*;
import DataGenerator.Generator;
import DataGenerator.Location;
import model.AuthorizationToken;
import model.Event;
import model.Person;
import model.User;
import service.response.FillResponse;

import java.io.IOException;
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
public class FillService {

    public FillResponse execute(String username, int generations) throws SQLException {
        FillResponse response = new FillResponse();
        Database db = new Database();

        try {
            UserDAO user_dao = new UserDAO();
            User user = user_dao.find(username);

            if(user == null) {
                response.setMessage("error Username incorrect.");
                response.setSuccess("false");
            }
            else if (generations < 0) {
                response.setMessage("error generations must be 1 or more.");
                response.setSuccess("false");
            }
            else {
                eraseAssociatedDataToGivenUser(user, username); // If there is any data in the database associated with the given user name, it is erased.

                // New root Person for fill
                PersonDAO personDAO = new PersonDAO();
                Person person = personDAO.find(user.getPersonID());
                String personID = person.getPersonID();
                String associatedUsername = person.getAssociatedUsername();
                String firstName = person.getFirstName();
                String lastName = person.getLastName();
                String gender = person.getGender();

                // Clean up
                personDAO.deletePersonFamily(person.getAssociatedUsername());
                EventDAO eventDAO = new EventDAO();
                eventDAO.deleteEventFamily(person.getAssociatedUsername());

                // create the new root person
                person = new Person(personID, associatedUsername, firstName, lastName, gender, null, null, null);
                String fatherID = UUID.randomUUID().toString().substring(0 ,7);
                String motherID = UUID.randomUUID().toString().substring(0 ,7);
                person.setFatherID(fatherID);
                person.setMotherID(motherID);
                personDAO.insert(person);

                // person birth event
                Generator generator = new Generator();
                Location location = generator.generateLocation();
                int birthYear = 1996;
                Event eventBirth = generateBirthEvent(person, birthYear, location);
                eventDAO.insert(eventBirth);

                // generate root person's parents (and the later generations)
                generateParents(person, birthYear, 0, generations, fatherID, motherID);

                int numPeopleAdded = numPeopleCalculation(generations);
                int numEventsAdded = numEventsCalculation(generations);

                response.setMessage("Successfully added " + numPeopleAdded + " persons and " + numEventsAdded + " events to the database.");
                response.setSuccess("true");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * Calculates the total number of people added based on the generations
     * @param generations
     * @return
     */
    private int numPeopleCalculation(int generations) {
        int numPeople = (int)(Math.pow(2, generations + 1) - 1);
        return numPeople;
    }

    /**
     * Calculates the total number of events added based on the generations
     * @param generations
     * @return
     */
    private int numEventsCalculation(int generations) {

        int numPeople = (int)(Math.pow(2, generations + 1) - 1);
        int numEvents = (numPeople * 3) - 2;
        return numEvents;
    }

    /**
     * If there is any data in the database associated with the given user name, it is erased.
     * @param user
     * @param username
     * @throws SQLException
     * @throws DataAccessException
     */
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
        String motherID = UUID.randomUUID().toString().substring(0 ,7);
        String fatherID = UUID.randomUUID().toString().substring(0 ,7);

        // Set person mother and father ID
        person.setMotherID(motherID);
        person.setFatherID(fatherID);

        // Finally put the person back into the Persons table
        person_dao.insert(person);
    }

    /**
     * Generates a birth event with person,location, and birthyear as parameters
     * @param person
     * @param location
     * @param birthYear
     * @return
     */
    private Event generateBirthEvent(Person person, int birthYear, Location location) {
        int year = birthYear;
        String eventID = UUID.randomUUID().toString();
        String eventType = "Birth";
        Event birthEvent = new Event(eventID, person.getAssociatedUsername(), person.getPersonID(), location.getLatitude(),
                location.getLongitude(), location.getCountry(), location.getCity(), eventType, year);
        return birthEvent;
    }

    /**
     * Generates the parents
     * @param person
     * @param birthYear
     * @param currentGeneration
     * @param totalGenerations
     * @param fatherID
     * @param motherID
     * @throws IOException
     * @throws DataAccessException
     */
    private void generateParents(Person person, int birthYear, int currentGeneration, int totalGenerations, String fatherID, String motherID) throws IOException, DataAccessException {
        if (currentGeneration >= totalGenerations) {
            return;
        }

        // Readjust the birth year
        birthYear = birthYear - 40;

        // PERSON
        // creates mother and father of person
        Person mother = generateFemalePerson(person.getAssociatedUsername());
        mother.setPersonID(motherID);
        Person father = generateMalePerson(person.getAssociatedUsername());
        father.setPersonID(fatherID);

        // Generate birth of parents
        Generator generator = new Generator();
        Location location1 = generator.generateLocation();
        Location location2 = generator.generateLocation();
        Event birthOfMother = generateBirthEvent(mother, birthYear, location1);
        Event birthOfFather = generateBirthEvent(father, birthYear - 1, location2);

        // Generate marriage event
        int marriageYear = birthYear + 22;
        Location locationMarriage = generator.generateLocation();
        Event marriageMother = generateMarriage(mother, locationMarriage, marriageYear);
        Event marriageFather = generateMarriage(father, locationMarriage, marriageYear);
        // Make parents married to each other
        mother.setSpouseID(fatherID);
        father.setSpouseID(motherID);

        // Generate death
        int deathYear1 = birthYear + 38;
        int deathYear2 = birthYear + 38;
        Location deathLocation = generator.generateLocation();
        Event deathOfMother = generateDeathEvent(mother, deathLocation, deathYear1);
        Event deathOfFather = generateDeathEvent(father, deathLocation, deathYear2);


        // Generate parents of parents personID
        String fatherOfFather = UUID.randomUUID().toString().substring(0 ,7);
        String motherOfFather = UUID.randomUUID().toString().substring(0 ,7);
        String fatherOfMother = UUID.randomUUID().toString().substring(0 ,7);
        String motherOfMother = UUID.randomUUID().toString().substring(0 ,7);

        // Set parent of parent ID
        if (currentGeneration <= totalGenerations - 2) {
            father.setFatherID(fatherOfFather);
            father.setMotherID(motherOfFather);
            mother.setFatherID(fatherOfMother);
            mother.setMotherID(motherOfMother);
        }

        PersonDAO person_dao = new PersonDAO();
        EventDAO event_dao = new EventDAO();
        UserDAO user_dao = new UserDAO();
        try {
            // INSERT PARENTS with personDAO
            person_dao.insert(father);
            person_dao.insert(mother);

            // INSERT EVENTS with personDAO
            event_dao.insert(birthOfMother);
            event_dao.insert(birthOfFather);
            event_dao.insert(marriageMother);
            event_dao.insert(marriageFather);
            event_dao.insert(deathOfMother);
            event_dao.insert(deathOfFather);
            currentGeneration++;

            generateParents(mother, birthYear, currentGeneration, totalGenerations, fatherOfMother, motherOfMother);
            generateParents(father, birthYear, currentGeneration, totalGenerations, fatherOfFather, motherOfFather);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a death event with person,location, and birthyear as parameters
     * @param person
     * @param location
     * @param deathYear
     * @return
     */
    private Event generateDeathEvent(Person person, Location location, int deathYear) {
        int year = deathYear;
        String eventID = UUID.randomUUID().toString();
        String eventType = "Death";
        Event deathEvent = new Event(eventID, person.getAssociatedUsername(), person.getPersonID(), location.getLatitude(),
                location.getLongitude(), location.getCountry(), location.getCity(), eventType, year);
        return deathEvent;
    }

    /**
     * Generates a marriage event with person,location, and birthyear as parameters
     * @param person
     * @param location
     * @param marriageYear
     * @return
     */
    private Event generateMarriage(Person person, Location location, int marriageYear) {
        int year = marriageYear;
        String eventID = UUID.randomUUID().toString();
        String eventType = "Marriage";
        Event marriageEvent = new Event(eventID, person.getAssociatedUsername(), person.getPersonID(), location.getLatitude(),
                location.getLongitude(), location.getCountry(), location.getCity(), eventType, year);
        return marriageEvent;

    }

    /**
     * Generates a random Male Person with given username
     * @param username
     * @return
     * @throws IOException
     * @throws DataAccessException
     */
    private Person generateMalePerson(String username) throws IOException, DataAccessException {
        Generator generator = new Generator();
        String maleName = generator.generateMaleName();
        String lastName = generator.generateSurname();
        String personID = UUID.randomUUID().toString().substring(0 ,7);
        String gender = "m";
        Person malePerson = new Person(personID, username, maleName, lastName, gender, null, null, null);
        return malePerson;
    }

    /**
     * Generates a random Female Person with given username
     * @param username
     * @return
     * @throws IOException
     * @throws DataAccessException
     */
    private Person generateFemalePerson(String username) throws IOException, DataAccessException {
        Generator generator = new Generator();
        String femaleName = generator.generateFemaleName();
        String lastName = generator.generateSurname();
        String personID = UUID.randomUUID().toString().substring(0 ,7);
        String gender = "f";
        Person femalePerson = new Person(personID, username, femaleName, lastName, gender, null, null ,null);
        return femalePerson;
    }
}