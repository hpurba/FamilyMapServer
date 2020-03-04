package handler;


import java.io.*;
import java.net.*;
import java.sql.SQLException;

import DAO.DataAccessException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.*;
import service.request.*;
import service.response.LoginResponse;
import service.services.Login;

public class LoginHandler implements HttpHandler {

    /**
     * Converts a given object to a json String
     * @param input
     * @param <T>
     * @return
     */
    private static <T> String serialize(T input) {
        // JSON is a format for storing any kind of data in a tree structure
        // Gson is a tool google made to translate objects to/from json
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(input);
    }

    /**
     * Converts a InputStream to a String
     * @param is
     * @return
     * @throws IOException
     */
    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    /**
     * This takes the JsonString I generated from the response object of clearing AND the outputStream for which I will insert the json into. then close it
     * @param str
     * @param os
     * @throws IOException
     */
    private void writeString(String str, OutputStream os) throws IOException {
        // writer vs stream, writer is better for strings and characters, stream is for bytes (like 1s and 0s)
        OutputStreamWriter sw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(sw);
        bw.write(str);  // This is where we put the jsonstring into the outputstream(but is really a buffered writer)
        bw.flush();     // sends the buffered writer
    }

    /**
     * Handles the httpExchange. Grabs the request body, uses it to register the user, then generates a response object to return to server.
     * @param httpExchange
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        try {
            boolean success = false;
            Login loginService = new Login();
            LoginResponse loginResponseObj = new LoginResponse();
            String JsonString = "";
            Gson gson = new Gson();

            // From httpExchange, grab the inputStream request Body as an inputStream
            InputStream requestBodyIS = httpExchange.getRequestBody();
            String reqJsonStr = readString(requestBodyIS); // Convert InputStream to a Json
            LoginRequest loginRequestObj = gson.fromJson(reqJsonStr, LoginRequest.class); // Json String to the request Object

            try{
                loginResponseObj = loginService.execute(loginRequestObj);    // this will give back to me a response object
            } catch (DataAccessException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JsonString = serialize(loginResponseObj);    // object to Json String
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); // this indicates the sending proceedure is about to start
            OutputStream responseBody = httpExchange.getResponseBody();
            writeString(JsonString, responseBody);
            responseBody.close();   // indicates "I'm done", closes the connection
        }
        catch (IOException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            httpExchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
