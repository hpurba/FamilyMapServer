package DAO;

import model.Event;
import model.User;
import service.response.EventResponse;

import java.sql.*;
import java.util.ArrayList;

public class EventDAO {

    public void insert(Event event) throws DataAccessException {
        Database db = new Database();
        Connection conn = db.openConnection();

        String sql = "INSERT INTO Events (EventID, AssociatedUserName, PersonID, Latitude, Longitude, " +
                "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setDouble(4, event.getLatitude());
            stmt.setDouble(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();   // executes the sql statement
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
            throw new DataAccessException("Error encountered while inserting event into the database");
        }
        finally {
            db.closeConnection(true);
        }
    }

    public Event find(String eventID) throws DataAccessException {
        Database db = new Database();
        Connection conn = db.openConnection();

        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE EventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();   // execute the query on the statement will return a list of all the rows that match

            // this could be a while loop later
            if (rs.next()) {
                event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                db.closeConnection(true);
                return event;
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
    public void clear() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.openConnection();

        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Events";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
        db.closeConnection(true);
    }

    public void deleteEventFamily(String userName) throws SQLException, DataAccessException {
        Database db = new Database();
        Connection conn = db.openConnection();

        String sql = "DELETE FROM Events WHERE AssociatedUserName = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            db.closeConnection(false);
            throw new DataAccessException("SQL Error encountered while deleting events with AssociatedUserName");
        }
        db.closeConnection(true);
    }

    public EventResponse getEvents(String username) throws DataAccessException {
        Database db = new Database();
        Connection conn = db.openConnection();

        Event event;
        ArrayList<Event> eventsArrayList = new ArrayList<>();
        ResultSet rs = null;
        EventResponse eventResponse = new EventResponse();

        String sql = "SELECT * FROM Events WHERE AssociatedUserName = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();   // execute the query on the statement will return a list of all the rows that match

            while (rs.next()) {
                event = new Event(rs.getString("EventID"), rs.getString("AssociatedUserName"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                eventsArrayList.add(event);
            }
//            eventResponse.setEvents(eventsArrayList);
//            return eventResponse;
        } catch (SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if(rs != null) {
                try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
                try { db.closeConnection(true); } catch (Exception e) {}
            }
        }

        if(eventsArrayList.size() > 0) {
            eventResponse.setEvents(eventsArrayList);
            eventResponse.setSuccess(true);
            return eventResponse;
        }
        else {
            eventResponse.setMessage("No events associated with username");
            eventResponse.setSuccess(false);
        }
        return eventResponse;
    }
}
