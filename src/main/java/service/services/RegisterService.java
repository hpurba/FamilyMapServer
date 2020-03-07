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
public class RegisterService {

    public RegisterResponse execute(RegisterRequest request) throws SQLException, DataAccessException {
        RegisterResponse response = new RegisterResponse();             // registering response object

        try{
            Gson gson = new Gson();                                         //  It can also be used to convert a JSON string to an equivalent Java object
            response = new RegisterResponse();
            // DAOs
            AuthorizationTokenDAO token_dao = new AuthorizationTokenDAO();
            UserDAO user_dao = new UserDAO();
            PersonDAO person_dao = new PersonDAO();

            // If the user already exists in the database, don't add. If the
            if ((user_dao.find(request.getUserName()) != null) || (person_dao.find(request.getUserName()) != null)) {
                response.setMessage("Username already exists, please choose a different one.");
                response.setSuccess("false");
            }
            else if (!request.getGender().equals("f") && !request.getGender().equals("m")) {
                response.setMessage("Gender must be either f or m.");
                response.setSuccess("false");
            }
            else {
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
                person.setAssociatedUsername(user.getUserName());
                // AUTHORIZATION TOKEN
                AuthorizationToken token = new AuthorizationToken(authorizationToken, user.getUserName());

                // Insertion into database
                user_dao.insert(user);
                person_dao.insert(person);
                token_dao.addAuthorizationToken(token);


                // 4 Generations
                FillService fillService = new FillService();
                fillService.execute(request.getUserName(), 4);

                //create response to the Register Request
                response.setAuthToken(authorizationToken);
                response.setUserName(user.getUserName());
                response.setPersonID(personID);
                response.setSuccess("true");
            }
            return response;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
}