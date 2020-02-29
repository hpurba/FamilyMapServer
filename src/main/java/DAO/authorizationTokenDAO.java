package DAO;

import model.AuthorizationToken;

import java.sql.SQLException;

public class authorizationTokenDAO {

    // Instantiation of Token
    public authorizationTokenDAO() throws SQLException {

    }

    // adding the token to the database
    public void addAuthorizationToken(AuthorizationToken token) throws SQLException {

    }

    // get a username from the token
    public String getUserName(String token) throws SQLException {
        return "userName variable goes here";
    }

    // validate the user with their (token and userName)
    public boolean validateToken(String token, String userName) throws SQLException {
        return false;
    }

    // removes all the tokens
    public void clear() throws SQLException {

    }

    // delete a specific token
    public void deleteToken(String token) throws SQLException {

    }
}