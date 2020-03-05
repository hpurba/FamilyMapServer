package DAO;

import model.Person;
import model.User;

import java.sql.*;
import java.util.ArrayList;

public class PersonDAO {

    // INSERTION

    /**
     * INSERTION
     *  Inserts a person into the database
     * @param person
     * @throws DataAccessException
     * @throws SQLException
     */
    public void insert(Person person) throws DataAccessException, SQLException {
        Database db = new Database();
        Connection conn = db.openConnection();

        String sql = "INSERT INTO Persons (PersonID, AssociatedUserName, FirstName, LastName, " +
                "Gender, FatherID, MotherID, SpouseID) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUserName());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
            throw new DataAccessException("Error encountered while inserting person into the database");
        }
        finally {
            db.closeConnection(true);
        }
    }

    /**
     * FIND
     * Finds a user in the Users table. Compiles ResultSet from its findings.
     * @param personID
     * @return
     * @throws DataAccessException
     * @throws SQLException
     */
    public Person find(String personID) throws DataAccessException, SQLException {
        Database db = new Database();
        Connection conn = db.openConnection();

        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE PersonID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("PersonID"), rs.getString("AssociatedUserName"),
                        rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"),
                        rs.getString("FatherID"), rs.getString("MotherID"), rs.getString("SpouseID"));
                db.closeConnection(true);
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
            throw new DataAccessException("Error encountered while finding person");
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
     * CLEAR
     * Clears all persons from the database
     * @throws DataAccessException
     * @throws SQLException
     */
    public void clear() throws DataAccessException, SQLException {
        Database db = new Database();
        Connection conn = db.openConnection();

        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Persons";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            db.closeConnection(false);
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
        db.closeConnection(true);
    }

    /**
     * Delete a user in Persons table
     * Uses the username to find instances of that person existing in the persons table to remove.
     * @param userName
     * @throws SQLException
     * @throws DataAccessException
     */
    public void deletePersonFamily(String userName) throws SQLException, DataAccessException {
        Database db = new Database();
        Connection conn = db.openConnection();

        String sql = "DELETE FROM Persons WHERE AssociatedUserName = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            db.closeConnection(false);
            throw new DataAccessException("SQL Error encountered while deleting person from persons tables where username came up");
        }
        db.closeConnection(true);
    }

    public ArrayList<Person> getAllPersons(String username) throws DataAccessException {
        Database db = new Database();
        Connection conn = db.openConnection();

        ArrayList<Person> personArrayList = new ArrayList<>();
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE PersonID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                person = new Person(rs.getString("PersonID"), rs.getString("AssociatedUserName"),
                        rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"),
                        rs.getString("FatherID"), rs.getString("MotherID"), rs.getString("SpouseID"));
                personArrayList.add(person);
            }
            db.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
            throw new DataAccessException("Error encountered while finding person");
        } finally {
            if(rs != null) {
                try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
                try { db.closeConnection(false); } catch (Exception e) {}       // This might be a problem
            }
        }
        return null;
    }
}