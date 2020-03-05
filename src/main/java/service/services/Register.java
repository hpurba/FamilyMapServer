package service.services;

import DAO.*;
import com.google.gson.Gson;
import model.AuthorizationToken;
import model.Person;
import model.User;
import service.request.RegisterRequest;
import service.response.RegisterResponse;

import java.sql.*;
import java.util.*;

/**
 *  Description: Creates a new user account, generates 4 generations of ancestor data for the new
 * user, logs the user in, and returns an auth token.
 * HTTP Method: POST
 * Auth Token Required: No
 */
public class Register {

    public RegisterResponse execute(RegisterRequest request) throws SQLException, DataAccessException {
        RegisterResponse response = new RegisterResponse();             // registering response object

        try{
            Gson gson = new Gson();                                         //  It can also be used to convert a JSON string to an equivalent Java object
            response = new RegisterResponse();
            // DAOs
            authorizationTokenDAO token_dao = new authorizationTokenDAO();
            UserDAO user_dao = new UserDAO();
            PersonDAO person_dao = new PersonDAO();

            // If the user already exists in the database, don't add. If the
            if (user_dao.existsUser(request.getUserName()) || person_dao.find(request.getUserName()) != null) {
                response.setMessage("Username already exists, please choose a different one.");
                response.setSuccess(false);
//            return response;
            } else {
                // Generate unique AuthorizationToken and PersonID
                String authorizationToken = UUID.randomUUID().toString();
                String personID = UUID.randomUUID().toString();

                String requestJsonStr = gson.toJson(request);                  // conversion to Json

                // USER
                User user = gson.fromJson(requestJsonStr, User.class);
                user.setPersonID(personID);
                // PERSON
                Person person = gson.fromJson(requestJsonStr, Person.class);   // grab the person
                person.setPersonID(personID);
                person.setAssociatedUserName(user.getUserName());
                // AUTHORIZATION TOKEN
                AuthorizationToken token = new AuthorizationToken(authorizationToken, user.getUserName());

                // Insertion into database
                user_dao.insert(user);
                person_dao.insert(person);
                token_dao.addAuthorizationToken(token);

                //create response to the Register Request
                response.setAuthorizationToken(authorizationToken);
                response.setUserName(user.getUserName());
                response.setPersonID(personID);
                response.setSuccess(true);
            }
            return response;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return response;
//        Gson gson = new Gson();                                         //  It can also be used to convert a JSON string to an equivalent Java object
//        authorizationTokenDAO token_dao = new authorizationTokenDAO();
//        RegisterResponse response = new RegisterResponse();             // registering response object
//        // Database db = null;
//        UserDAO user_dao = new UserDAO();
//        PersonDAO person_dao = new PersonDAO();
//
//        // If the user already exists in the database, don't add
//        if (user_dao.existsUser(request.getUserName()) || person_dao.find(request.getUserName()) != null) {
//            response.setMessage("Username already exists, please choose a different one.");
//            response.setSuccess(false);
////            return response;
//        } else {
//            // Generate unique AuthorizationToken and PersonID
//            String authorizationToken = UUID.randomUUID().toString();
//            String personID = UUID.randomUUID().toString();
//
//            String requestJsonStr = gson.toJson(request);                  // conversion to Json
//
//            // USER
//            User user = gson.fromJson(requestJsonStr, User.class);
//            user.setPersonID(personID);
//            // PERSON
//            Person person = gson.fromJson(requestJsonStr, Person.class);   // grab the person
//            person.setPersonID(personID);
//            person.setAssociatedUserName(user.getUserName());
//            // AUTHORIZATION TOKEN
//            AuthorizationToken token = new AuthorizationToken(authorizationToken, user.getUserName());
//
//            // Insertion into database
//            user_dao.insert(user);
//            person_dao.insert(person);
//            token_dao.addAuthorizationToken(token);
//
//            //create response to the Register Request
//            response.setAuthorizationToken(authorizationToken);
//            response.setUserName(user.getUserName());
//            response.setPersonID(personID);
//            response.setSuccess(true);
//        }
//        return response;
    }
}


        // Establish connection to database
//        try {
//            db = new Database();
//            Connection conn = db.openConnection();          // Open connection to database
//            UserDAO user_dao = new UserDAO(conn);           // Data Access Object: User
//            PersonDAO person_dao = new PersonDAO(conn);
//
//
//
//            // User already exists, cannot register
//            if(user_dao.existsUser(request.getUserName())) {
//                response.setMessage("Username already exists, please choose a different one.");
//                response.setSuccess(false);
//                return response;
//            }
//
//            // Generate random IDs
//            String authorizationToken = UUID.randomUUID().toString();   // Was this: (String.valueOf(UUID.randomUUID().getLeastSignificantBits())).toString();
//            String personID = UUID.randomUUID().toString();
//
//            // Convert the request to a json String
//            String requestJsonStr = gson.toJson(request);                  // conversion to Json
//
//            // USER
//            User user = gson.fromJson(requestJsonStr, User.class);
//            user.setPersonID(personID);
//            // PERSON
//            Person person = gson.fromJson(requestJsonStr, Person.class);   // grab the person
//            person.setPersonID(personID);
//            person.setAssociatedUserName(user.getUserName());
//            // AUTHORIZATION TOKEN
//            AuthorizationToken token = new AuthorizationToken(authorizationToken, user.getUserName());
//
//            // Insertion into database
//            user_dao.insert(user);
//            person_dao.insert(person);
//            token_dao.addAuthorizationToken(token);
//
//
//            //create response
//            response.setAuthorizationToken(authorizationToken);
//            response.setUserName(user.getUserName());
//            response.setPersonID(personID);
//            response.setSuccess(true);
//
//
//            System.out.println("Uh oh something went terribly wrong! Before");
//            System.out.println("Uh oh something went terribly wrong! After");
//
//            db.closeConnection(true);   // this closes the connection
//        } catch (DataAccessException e) {
//            db.closeConnection(false);   // this closes the connection
//            e.printStackTrace();
//        }
