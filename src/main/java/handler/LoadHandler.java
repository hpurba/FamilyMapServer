package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.request.LoadRequest;
import service.response.LoadResponse;
import service.services.ClearService;
import service.services.LoadService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.sql.SQLException;

public class LoadHandler extends HandlerGeneric implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        // Clear all existing data
        ClearService clearService = new ClearService();
        try {
            clearService.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        LoadService loadService = new LoadService();
        LoadResponse loadResponseObj = new LoadResponse();

        InputStream requestBodyIS = httpExchange.getRequestBody();
        String reqJsonStr = readString(requestBodyIS);
        LoadRequest loadRequestObj = gson.fromJson(reqJsonStr, LoadRequest.class);

        try{
            loadResponseObj = loadService.execute(loadRequestObj);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String JsonString = "";
        JsonString = serialize(loadResponseObj);
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStream responseBody = httpExchange.getResponseBody();
        writeString(JsonString, responseBody);
        responseBody.close();
    }
}
