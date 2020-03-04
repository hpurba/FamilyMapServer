package DAO;

import model.AuthorizationToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class authorizationTokenDAO {

    // Instantiation of Token
    public authorizationTokenDAO() throws SQLException {

    }

    // adding the token to the database
    public void addAuthorizationToken(AuthorizationToken token) throws SQLException, DataAccessException {
        Database db = new Database();
        Connection conn = db.openConnection();

        String sql = "INSERT INTO AuthorizationTokens (Token, UserName) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token.getToken());
            stmt.setString(2, token.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
            throw new DataAccessException("Error encountered while inserting into the database");
        }
        finally {
            // try { conn.close(); } catch (Exception e) { /* ignored */ }
            try {
                conn.commit();
                conn.close();
            } catch (Exception e) { /* ignored */ }
        }
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