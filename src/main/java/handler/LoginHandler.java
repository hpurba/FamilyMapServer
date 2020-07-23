package handler;

import java.io.*;
import java.net.*;
import java.sql.SQLException;

import DAO.DataAccessException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.*;
import service.request.*;
import service.response.LoginResponse;
import service.response.Response;
import service.services.LoginService;

public class LoginHandler extends HandlerGeneric implements HttpHandler {

    /**
     * Handles the httpExchange. Grabs the request body, uses it to register the user, then generates a response object to return to server.
     *
     * @param httpExchange
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        LoginService loginService = new LoginService();
        LoginResponse loginResponseObj = new LoginResponse();
        String JsonString = "";
        Gson gson = new Gson();
        // From httpExchange, grab the inputStream request Body as an inputStream
        InputStream requestBodyIS = httpExchange.getRequestBody();
        String reqJsonStr = readString(requestBodyIS); // Convert InputStream to a Json
        LoginRequest loginRequestObj = gson.fromJson(reqJsonStr, LoginRequest.class); // Json String to the request Object

        try {
            loginResponseObj = loginService.execute(loginRequestObj);    // this will give back to me a response object
            if (loginResponseObj.getSuccess() == "true") {
                JsonString = serialize(loginResponseObj);
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream responseBody = httpExchange.getResponseBody();
                writeString(JsonString, responseBody);
                responseBody.close();
            } else {
                JsonString = serialize(loginResponseObj);
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                OutputStream responseBody = httpExchange.getResponseBody();
                writeString(JsonString, responseBody);
                responseBody.close();
            }
        } catch (SQLException e) {
            JsonString = serialize(loginResponseObj);
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            OutputStream responseBody = httpExchange.getResponseBody();
            writeString(JsonString, responseBody);
            responseBody.close();
            e.printStackTrace();
        } catch (DataAccessException e) {
            JsonString = serialize(loginResponseObj);
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            OutputStream responseBody = httpExchange.getResponseBody();
            writeString(JsonString, responseBody);
            responseBody.close();
            e.printStackTrace();
        }
    }
}