package handler;

import DAO.AuthorizationTokenDAO;
import DAO.DataAccessException;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.response.EventIDResponse;
import service.response.EventResponse;
import service.services.EventService;
import service.services.EventIDService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.List;

public class EventHandler extends HandlerGeneric implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String urlPathGiven = httpExchange.getRequestURI().toString();
        String[] requestData = urlPathGiven.split("/");             // Splits up the url path into an Array of Strings
        String eventID;
        String JsonString = "";
        Gson gson = new Gson();

        AuthorizationTokenDAO auth_dao = new AuthorizationTokenDAO();
        EventService eventService = new EventService();
        EventIDService eventIDService = new EventIDService();
        EventResponse eventResponseObj = new EventResponse();
        EventIDResponse eventIDResponseObj = new EventIDResponse();

        try {
            // This API will return ALL events for ALL family members of the current user.
            // The current user is determined from the provided authorization authToken (which is required for this call).
            // The returned JSON object contains "data" which is an array of event objects. Authorization authToken is required.
            // get all events
            if(requestData.length == 2) {
                // If the url length is 2, return ALL EVENTS OF ALL FAMILY MEMBERS of current user

                // grab username using the provided Auth Token
                List<String> authToken = httpExchange.getRequestHeaders().get("Authorization");
                String token = authToken.get(0);
                String username;

                try {
                    username = auth_dao.getUserName(token);
                    eventResponseObj = eventService.execute(username);  //  Attempt to fill using the fillService
                } catch (SQLException e) {
                    JsonString = serialize(eventResponseObj);
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    OutputStream responseBody = httpExchange.getResponseBody();
                    writeString(JsonString, responseBody);
                    responseBody.close();
                    e.printStackTrace();
                } catch (DataAccessException e) {
                    JsonString = serialize(eventResponseObj);
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    OutputStream responseBody = httpExchange.getResponseBody();
                    writeString(JsonString, responseBody);
                    responseBody.close();
                    e.printStackTrace();
                }
                if (eventResponseObj.getSuccess() == "true") {
                    JsonString = serialize(eventResponseObj);                                           // Response Object to Json String
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);                 // Indicates the sending procedure is about to start
                    OutputStream responseBody = httpExchange.getResponseBody();                        //  Grabs the response body (OutputStream) from the httpExchange
                    writeString(JsonString, responseBody);                                             // Writes the Json into the response body / OutputStream
                    responseBody.close();                                                              // indicates "I'm done", closes the httpExchange
                } else {
                    JsonString = serialize(eventResponseObj);
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    OutputStream responseBody = httpExchange.getResponseBody();
                    writeString(JsonString, responseBody);
                    responseBody.close();
                }
            }
            // If url length is 3, it means eventID is provided
            else {
                eventID = requestData[2];

                // grab username using the provided Auth Token
                List<String> authToken = httpExchange.getRequestHeaders().get("Authorization");
                String token = authToken.get(0);
                String username;

                try {
                    username = auth_dao.getUserName(token);
                    eventIDResponseObj = eventIDService.execute(username, eventID);  //  Attempt to fill using the fillService
                } catch (SQLException e) {
                    JsonString = serialize(eventIDResponseObj);
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    OutputStream responseBody = httpExchange.getResponseBody();
                    writeString(JsonString, responseBody);
                    responseBody.close();
                    e.printStackTrace();
                } catch (DataAccessException e) {
                    JsonString = serialize(eventIDResponseObj);
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    OutputStream responseBody = httpExchange.getResponseBody();
                    writeString(JsonString, responseBody);
                    responseBody.close();
                    e.printStackTrace();
                }

                if (eventIDResponseObj.getSuccess() == "true") {
                    JsonString = serialize(eventIDResponseObj);
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream responseBody = httpExchange.getResponseBody();
                    writeString(JsonString, responseBody);
                    responseBody.close();
                } else {
                    JsonString = serialize(eventIDResponseObj);
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    OutputStream responseBody = httpExchange.getResponseBody();
                    writeString(JsonString, responseBody);
                    responseBody.close();
                }
            }
        } catch (IOException e) {
            JsonString = serialize(eventIDResponseObj);
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            OutputStream responseBody = httpExchange.getResponseBody();
            writeString(JsonString, responseBody);
            responseBody.close();
            e.printStackTrace();
        }
    }
}
