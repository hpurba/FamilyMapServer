package DAO;
import model.User;
import java.sql.*;


public class UserDAO {
    /**
     * INSERTION
     * Inserts a user into the database
     * @param user
     * @throws DataAccessException
     * @throws SQLException
     */
    public void insert(User user) throws DataAccessException, SQLException {
        Database db = new Database();
        Connection conn = db.openConnection();

        String sql = "INSERT INTO Users (UserName, Password, Email, FirstName, LastName, " +
                "Gender, PersonID) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
            throw new DataAccessException("Error encountered while inserting user into the database");
        }
        finally {
            db.closeConnection(true);
        }
    }

    /**
     * FIND
     * Finds a user in the Users table. Compiles ResultSet from its findings.
     * @param userName
     * @return
     * @throws DataAccessException
     * @throws SQLException
     */
    public User find(String userName) throws DataAccessException, SQLException {
        Database db = new Database();
        Connection conn = db.openConnection();

        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM Users WHERE UserName = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(rs.getString("UserName"), rs.getString("Password"),
                        rs.getString("Email"), rs.getString("FirstName"), rs.getString("LastName"),
                        rs.getString("Gender"), rs.getString("PersonID"));
                db.closeConnection(true);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
            throw new DataAccessException("Error encountered while finding username");
        } finally {
            if(rs != null) {
                try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
                try { db.closeConnection(false); } catch (Exception e) {}
            }
        }
        return null;
    }

    /**
     * CLEAR
     * Clears all users from the database
     * @throws DataAccessException
     * @throws SQLException
     */
    public void clear() throws DataAccessException, SQLException {
        Database db = new Database();
        Connection conn = db.openConnection();

        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Users";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            db.closeConnection(false);
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
        db.closeConnection(true);
    }
}
