package service.services;

import DAO.DataAccessException;
import DAO.PersonDAO;
import DAO.UserDAO;
import DAO.authorizationTokenDAO;
import com.google.gson.Gson;
import model.AuthorizationToken;
import model.User;
import service.request.LoginRequest;
import service.response.LoginResponse;

import java.sql.SQLException;
import java.util.UUID;

public class Login {

    /**
     * Logs in the user and returns and Authtoken
     * @param request
     * @return
     * @throws SQLException
     * @throws DataAccessException
     */
    public LoginResponse execute(LoginRequest request) throws SQLException, DataAccessException {
        LoginResponse response = new LoginResponse();

        try{
            Gson gson = new Gson();
            response = new LoginResponse();

            UserDAO user_dao = new UserDAO();
            PersonDAO person_dao = new PersonDAO();
            authorizationTokenDAO token_dao = new authorizationTokenDAO();

            User user = user_dao.find(request.getUserName());   // First grab the user

            // If the password is a match to the user, then continue the login
            if(user != null && user.getPassword().equals(request.getPassword())){
                String authorizationToken = UUID.randomUUID().toString();

                // Add the auth token
                AuthorizationToken token = new AuthorizationToken(authorizationToken, user.getUserName());
                token_dao.addAuthorizationToken(token);

                // Generate the response
                response.setAuthToken(authorizationToken);
                response.setUserName(user.getUserName());
                response.setPersonID(user.getPersonID());
                response.setSuccess(true);
                return response;
            }
            else{
                response.setMessage("Wrong Username or Password");
                response.setSuccess(false);
                return response;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}