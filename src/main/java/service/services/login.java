package service.services;

import service.request.loginRequest;
import service.response.loginResponse;

import java.sql.SQLException;

/**
 * URL Path: /user/login
 * Description: Logs in the user and returns an auth token.
 * HTTP Method: POST
 * Auth Token Required: No
 */
public class login {

    public loginResponse execute(loginRequest request) throws SQLException {
        loginResponse response = new loginResponse();

        // variables, initialization, etc.

        return response;
    }
}