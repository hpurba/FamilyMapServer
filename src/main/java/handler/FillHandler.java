package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.response.FillResponse;
import service.services.FillService;

import java.io.*;
import java.net.HttpURLConnection;
import java.sql.SQLException;

public class FillHandler extends HandlerGeneric implements HttpHandler {
    /**
     * Handles the httpExchange. Populates the server's database with generated data
     * for the specified userName.
     * @param httpExchange
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

//        System.out.println("Now inside the fill handler");

        try {
            String urlPathGiven = httpExchange.getRequestURI().toString();
            String[] requestData = urlPathGiven.split("/");             // Splits up the url path into an Array of Strings
            String userName;
            String numGenerations;

            // Case where the number of generations is not specified, so the default of 4 is implemented
            if(requestData.length == 3) {
                userName = requestData[2];
                numGenerations = "4";
            }
            else {
                userName = requestData[2];
                numGenerations = requestData[3];
            }

            FillService fillService = new FillService();
            FillResponse fillResponseObj = new FillResponse();

            // Attempt to fill using the fillService
            try{
                fillResponseObj = fillService.execute(userName, Integer.parseInt(numGenerations));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String JsonString = "";
            Gson gson = new Gson();

            JsonString = serialize(fillResponseObj);                                       // Response Object to Json String
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);     // Indicates the sending procedure is about to start
            OutputStream responseBody = httpExchange.getResponseBody();                        //  Grabs the response body (OutputStream) from the httpExchange
            writeString(JsonString, responseBody);                                             // Writes the Json into the response body / OutputStream
            responseBody.close();                                                              // indicates "I'm done", closes the httpExchange
        }
        catch (IOException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            httpExchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
