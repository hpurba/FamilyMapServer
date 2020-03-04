package DAO;
import model.User;
import java.sql.*;


public class UserDAO {

//    private Connection conn;
//
//    public UserDAO(Connection conn)
//    {
//        this.conn = conn;
//    }


    // INSERTION
    public void insert(User user) throws DataAccessException, SQLException {
        Database db = new Database();
        Connection conn = db.openConnection();

//        Statement statement = conn.createStatement();
//        String sql = "INSERT INTO USERS " +
//                "(UserName, Password, Email, FirstName, LastName, Gender, PersonID) VALUES ('" +
//                user.getUserName() + "', '" +
//                user.getPassword() + "', '" +
//                user.getEmail() + "', '" +
//                user.getFirstName() + "', '" +
//                user.getLastName() + "', '" +
//                user.getGender() + "', '" +
//                user.getGender() + "')";
//        statement.executeUpdate(sql);
//
//        statement.close();
//        conn.close();
//        db.closeConnection(true);


        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
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
            throw new DataAccessException("Error encountered while inserting into the database");
        }
        finally {
            // try { conn.close(); } catch (Exception e) { /* ignored */ }
            try {
                conn.commit();
                conn.close();
//                db.closeConnection(true);
            } catch (Exception e) { /* ignored */ }
        }
//        db.closeConnection(true);
    }

    // RETRIEVE INFORMATION (FIND)
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
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
            throw new DataAccessException("Error encountered while finding person");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        db.closeConnection(true);

        return null;
    }

    // clear all users from the database
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

    // delete a single user (current one) from the database
    public void delete(String token, String userName) throws SQLException {


    }

    // check if a user exists by their userName
    public boolean existsUser(String userName) throws SQLException, DataAccessException {
        Database db = new Database();
        Connection conn = db.openConnection();

        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM Users WHERE UserName = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            db.closeConnection(false);
            e.printStackTrace();
//            throw new DataAccessException("Error encountered while finding person");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    db.closeConnection(false);
                    e.printStackTrace();
                }
            }
        }
        db.closeConnection(true);

        return false;
    }
}



/*
 The 3 methods you will implement inside each DAO class will handle inserting new data
 into the table, retrieving information from a table, and clearing all information from the
 table.
 */