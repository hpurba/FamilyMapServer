package DAO;

import model.AuthorizationToken;
import model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorizationTokenDAO {

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
            try {
                conn.commit();
                conn.close();
            } catch (Exception e) { /* ignored */ }
        }
    }

    // get a username from the token
    public String getUserName(String token) throws SQLException, DataAccessException {
        Database db = new Database();
        Connection conn = db.openConnection();

        Event event;
        ResultSet rs = null;
        String sql = "SELECT UserName FROM AuthorizationTokens WHERE Token = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            rs = stmt.executeQuery();
            if (rs.next()) {
                String username = rs.getString(1);
                return username;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if(rs != null) {
                try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
                try { db.closeConnection(false); } catch (Exception e) {}
            }
        }
        return null;
    }

    public AuthorizationToken findAuthToken(String authToken) throws DataAccessException {
        Database db = new Database();
        Connection conn = db.openConnection();
        AuthorizationToken authTokenM;
        ResultSet rs = null;

        String sql = "SELECT * FROM AuthorizationTokens WHERE Token = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authTokenM = new AuthorizationToken( rs.getString("Token"), rs.getString("Username"));
                return authTokenM;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}