package handler;

import DAO.AuthorizationTokenDAO;
import DAO.DataAccessException;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Person;
import service.response.PersonIDResponse;
import service.response.PersonResponse;
import service.services.PersonIDService;
import service.services.PersonService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.List;

public class PersonHandler extends HandlerGeneric implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String urlPathGiven = httpExchange.getRequestURI().toString();
        String[] requestData = urlPathGiven.split("/");             // Splits up the url path into an Array of Strings
        String personID;

        // 0: ""
        // 1: "person"
        // 2: "personID"

        try {
            AuthorizationTokenDAO auth_dao = new AuthorizationTokenDAO();
            PersonService personService = new PersonService();
            PersonIDService personIDService = new PersonIDService();
            PersonResponse personResponseObj = new PersonResponse();
            PersonIDResponse personIDResponseObj = new PersonIDResponse();

            // This API will return ALL events for ALL family members of the current user.
            // The current user is determined from the provided authorization authToken (which is required for this call).
            // The returned JSON object contains "data" which is an array of event objects. Authorization authToken is required.
            // get all events
            if(requestData.length == 2) {
                // If the url length is 2, return ALL EVENTS OF ALL FAMILY MEMBERS of current user

                // grab username using the provided Auth Token
                List<String> authToken = httpExchange.getRequestHeaders().get("Authorization");
                String token = authToken.get(0);    // System.out.print(authToken.get(0));
                String username;

                try {
                    username = auth_dao.getUserName(token);
                    personResponseObj = personService.execute(username);  //  Attempt to fill using the fillService
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (DataAccessException e) {
                    e.printStackTrace();
                }

                String JsonString = "";
                Gson gson = new Gson();

                JsonString = serialize(personResponseObj);                                       // Response Object to Json String
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);     // Indicates the sending procedure is about to start
                OutputStream responseBody = httpExchange.getResponseBody();                        //  Grabs the response body (OutputStream) from the httpExchange
                writeString(JsonString, responseBody);                                             // Writes the Json into the response body / OutputStream
                responseBody.close();                                                              // indicates "I'm done", closes the httpExchange
            }
            // If url length is 3, it means eventID is provided
            else {
                personID = requestData[2];

                // grab username using the provided Auth Token
                List<String> authToken = httpExchange.getRequestHeaders().get("Authorization");
                String token = authToken.get(0);    // System.out.print(authToken.get(0));
                String username;

                try {
                    username = auth_dao.getUserName(token);
                    personIDResponseObj = personIDService.execute(username, personID);  //  Attempt to fill using the fillService
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (DataAccessException e) {
                    e.printStackTrace();
                }

                String JsonString = "";
                Gson gson = new Gson();

                JsonString = serialize(personIDResponseObj);                                       // Response Object to Json String
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);     // Indicates the sending procedure is about to start
                OutputStream responseBody = httpExchange.getResponseBody();                        //  Grabs the response body (OutputStream) from the httpExchange
                writeString(JsonString, responseBody);                                             // Writes the Json into the response body / OutputStream
                responseBody.close();                                                              // indicates "I'm done", closes the httpExchange
            }
        } catch (IOException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            httpExchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
