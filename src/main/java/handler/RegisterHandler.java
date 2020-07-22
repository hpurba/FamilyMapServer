package handler;

import DAO.DataAccessException;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.request.RegisterRequest;
import service.response.RegisterResponse;
import service.services.RegisterService;

import java.io.*;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.sql.SQLException;

public class RegisterHandler extends HandlerGeneric implements HttpHandler {

    /**
     * Handles the httpExchange. Grabs the request body, uses it to register the user,
     * then generates a response object to return to server.
     * @param httpExchange
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        try {
            RegisterService registerService = new RegisterService();
            RegisterResponse registerResponseObj = new RegisterResponse();
            String JsonString = "";
            Gson gson = new Gson();

            InputStream requestBodyIS = httpExchange.getRequestBody();                              // Grabs requestBody from httpExchange, which is an InputStream
            String reqJsonStr = readString(requestBodyIS);                                          // Convert request InputStream to a Json String
            RegisterRequest registerRequestObj = gson.fromJson(reqJsonStr, RegisterRequest.class); // Json String to the appropriate request Object

            try{
                registerResponseObj = registerService.execute(registerRequestObj);                  // Execution of registering. Done by the register service
            } catch (DataAccessException e) {
                 e.printStackTrace();
            } catch (SQLException e) {
                 e.printStackTrace();
            }

            JsonString = serialize(registerResponseObj);                                       // Response Object to Json String
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);     // Indicates the sending procedure is about to start
            OutputStream responseBody = httpExchange.getResponseBody();                        //  Grabs the response body (OutputStream) from the httpExchange
            writeString(JsonString, responseBody);                                             // Writes the Json into the response body / OutputStream
            responseBody.close();                                                              // indicates "I'm done", closes the httpExchange
        }
        catch (IOException e) {
//            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            httpExchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}