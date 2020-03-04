package DAO;

import model.Person;
import model.User;

import java.sql.*;

public class PersonDAO {

    // INSERTION
    public void insert(Person person) throws DataAccessException, SQLException {
        Database db = new Database();
        Connection conn = db.openConnection();

        String sql = "INSERT INTO Persons (PersonID, AssociatedUserName, FirstName, LastName, " +
                "Gender, FatherID, MotherID, SpouseID) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
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
            throw new DataAccessException("Error encountered while inserting into the database");
        }
        finally {
            try {
                conn.commit();
                conn.close();
            } catch (Exception e) { /* ignored */ }
        }


        //        try {
//            Statement statement = conn.createStatement();
//            String sql = "INSERT INTO PERSONS " +
//                    "(PersonID, AssociatedUserName, FirstName, LastName, Gender, FatherID, MotherID, SpouseID) VALUES ('" +
//                    person.getPersonID() + "', '" +
//                    person.getAssociatedUserName() + "', '" +
//                    person.getFirstName() + "', '" +
//                    person.getLastName() + "', '" +
//                    person.getGender() + "', '" +
//                    person.getFatherID() + "', '" +
//                    person.getMotherID() + "', '" +
//                    person.getSpouseID() + "')";
//            statement.executeUpdate(sql);
//
//            statement.close();
//            conn.commit();
//            conn.close();
////            db.closeConnection(true);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
    }

    // RETRIEVE INFORMATION (FIND)
    public Person find(String userID) throws DataAccessException, SQLException {
        Database db = new Database();
        Connection conn = db.openConnection();

        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE PersonID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("PersonID"), rs.getString("AssociatedUserName"),
                        rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"),
                        rs.getString("FatherID"), rs.getString("MotherID"), rs.getString("SpouseID"));
                return person;
            }
        } catch (SQLException e) {
            db.closeConnection(false);
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
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

        return null;
    }


    // clear/delete all people from the database
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

    // delete a single person from the database
    public void deletePerson(String personName) throws SQLException {

    }

    // check if a person exists
    public boolean existsPerson(String personName) throws SQLException {
        return false;
    }

    public Person[] getPersons(User username) throws DataAccessException {
        return null;
    }
}