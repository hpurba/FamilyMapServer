package service.services;

import DAO.*;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import model.AuthorizationToken;
import model.User;
import org.junit.jupiter.api.Assertions;
import service.request.LoginRequest;
import service.response.LoginResponse;

import java.net.HttpURLConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class LoginService {

    /**
     * Logs in the user and returns and Authtoken
     * @param request
     * @return
     * @throws SQLException
     * @throws DataAccessException
     */
    public LoginResponse execute(LoginRequest request) throws SQLException, DataAccessException {
        // WORKING ONE >>>>

        boolean isSuccess = false;
        Connection conn;

        String authToken = UUID.randomUUID().toString().substring(0 ,7);
        Database db = new Database();
        LoginResponse response = new LoginResponse();
        try {
            conn = db.openConnection();
            AuthorizationToken authTokenM = new AuthorizationToken(authToken, request.getUserName());
            AuthorizationTokenDAO authTokenDAO = new AuthorizationTokenDAO();
            authTokenDAO.addAuthorizationToken(authTokenM);
            UserDAO userDAO = new UserDAO();
            User userM = userDAO.find(request.getUserName());

            if (userM == null) {
                response.setAuthToken("null");
                response.setPersonID("null");
                response.setMessage("error : Username does not exist.");
//                response.setMessage(null);
                response.setSuccess("false");
                return response;
            }
            else if (!(userM.getPassword().equals(request.getPassword()))) {
                response.setAuthToken("null");
                response.setPersonID("null");
                response.setMessage("error : Password does not match.");
                response.setSuccess("false");
                return response;
            }
            else {
                response.setAuthToken(authToken);
                response.setUserName(request.getUserName());
                response.setPersonID(userM.getPersonID());
                isSuccess = true;
                response.setSuccess("true");
            }
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            try {
                db.closeConnection(false);
                return response;
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
        }
        return response;





//        LoginResponse response = new LoginResponse();
//
//        try{
//            Gson gson = new Gson();
//            response = new LoginResponse();
//
//            UserDAO user_dao = new UserDAO();
//            PersonDAO person_dao = new PersonDAO();
//            AuthorizationTokenDAO token_dao = new AuthorizationTokenDAO();
//
//            User user = user_dao.find(request.getUserName());   // First grab the user
//
//            // If the password is a match to the user, then continue the login
//            if(user != null && user.getPassword().equals(request.getPassword())){
//                String authorizationToken = UUID.randomUUID().toString();
//
//                // Add the auth token
//                AuthorizationToken token = new AuthorizationToken(authorizationToken, user.getUserName());
//                token_dao.addAuthorizationToken(token);
//
//                // Generate the response
//                response.setAuthToken(authorizationToken);
//                response.setUserName(user.getUserName());
//                response.setPersonID(user.getPersonID());
//                response.setSuccess("true");
//                return response;
//            }
//            else{
//                response.setMessage("Error, Wrong Username or Password");
//                response.setSuccess("false");
//                return response;
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        return response;
    }
}