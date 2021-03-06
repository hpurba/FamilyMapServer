package service.services;

import DAO.DataAccessException;
import DAO.Database;
import service.response.ClearResponse;


/**
 * URL Path: /clear
 * Description: Deletes ALL data from the database, including user accounts, auth tokens, and
 * generated person and event data.
 * HTTP Method: POST
 * Auth Token Required: No
 * Request Body: None
 * Errors: Internal server error
 */
public class ClearService {
    public ClearResponse execute() {
        //create DAOs
        ClearResponse response = new ClearResponse();
        //clear all tables
        Database db = new Database();
        try {
            db.openConnection();
            db.clearTables();
            response.setMessage("Clear succeeded.");
            response.setSuccess("true");
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            try {
                db.closeConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
            response.setMessage("Error clearing database.");
            response.setSuccess("false");
        }
        return response;
    }
}