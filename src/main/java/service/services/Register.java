package service.services;

import DAO.authorizationTokenDAO;
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


    public RegisterResponse execute(RegisterRequest request) throws SQLException {
        // Variables
        Gson gson = new Gson();                                         //  It can also be used to convert a JSON string to an equivalent Java object
        // UserDAO user_dao = new UserDAO();                               // new user DAO, data access object (DAO) is a pattern that provides an abstract interface to some type of database or other persistence mechanism. By mapping application calls to the persistence layer, the DAO provides some specific data operations without exposing details of the database. This isolation supports the single responsibility principle.
        authorizationTokenDAO token_dao = new authorizationTokenDAO();  // this may not be required
        // PersonDAO person_dao = new PersonDAO();
        RegisterResponse response = new RegisterResponse();             // registering response object


        // Grabs the username from the request object, then the user_dao will check if this user exists.
//        if(user_dao.existsUser(request.getUserName())) {
//            response.setMessage("Username already exists, please choose another.");
//            return response;
//        }

        // USER DOESN'T EXIST (make one) --->>>
        String personID = (String.valueOf(UUID.randomUUID().getLeastSignificantBits())).toString();
        String authorizationToken = (String.valueOf(UUID.randomUUID().getLeastSignificantBits())).toString();

        //create USER and PERSON and AUTH TOKEN, add to database
        String requestData = gson.toJson(request);                  // conversion to Json
        User user = gson.fromJson(requestData, User.class);         // grab the user
        Person person = gson.fromJson(requestData, Person.class);   // grab the person

        // set the personID for both the user and the person
        user.setPersonID(personID);
        person.setPersonID(personID);

        // makes an authorization token and adds it to: user_dao and token_dao
        AuthorizationToken token = new AuthorizationToken(authorizationToken, user.getUserName());
        // user_dao.insert(user);
        token_dao.addAuthorizationToken(token);

        //create 4 generations of person (0-4) and events for each


        //create response
        response.setUserName(user.getUserName());
        response.setPersonID(personID);
        response.setAuthorizationToken(authorizationToken);

        return response;
    }
}

