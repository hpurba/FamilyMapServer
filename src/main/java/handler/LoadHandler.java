package handler;

import DAO.DataAccessException;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.request.LoadRequest;
import service.response.LoadResponse;
import service.services.Clear;
import service.services.Load;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.sql.SQLException;

public class LoadHandler extends HandlerGeneric implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        // Clear all existing data
        Clear clearService = new Clear();
        try {
            clearService.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Load loadService = new Load();
        LoadResponse loadResponseObj = new LoadResponse();

        InputStream requestBodyIS = httpExchange.getRequestBody();                              // Grabs requestBody from httpExchange, which is an InputStream
        String reqJsonStr = readString(requestBodyIS);                                          // Convert request InputStream to a Json String
        LoadRequest loadRequestObj = gson.fromJson(reqJsonStr, LoadRequest.class); // Json String to the appropriate request Object

        try{
            loadResponseObj = loadService.execute(loadRequestObj);                  // Execution of registering. Done by the register service
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String JsonString = "";
        JsonString = serialize(loadResponseObj);                                       // Response Object to Json String
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);     // Indicates the sending procedure is about to start
        OutputStream responseBody = httpExchange.getResponseBody();                        //  Grabs the response body (OutputStream) from the httpExchange
        writeString(JsonString, responseBody);                                             // Writes the Json into the response body / OutputStream
        responseBody.close();
    }
}
