package service.services;

import service.response.clearResponse;


/**
 * URL Path: /clear
 * Description: Deletes ALL data from the database, including user accounts, auth tokens, and
 * generated person and event data.
 * HTTP Method: POST
 * Auth Token Required: No
 * Request Body: None
 * Errors: Internal server error
 */
public class clear {


    public clearResponse execute() {
        //create DAOs
        clearResponse response = new clearResponse();

        //clear all tables

        // try catch with clearing every part in the sql table

        return response;
    }
}