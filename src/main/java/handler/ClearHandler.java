package handler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.services.Clear;
import service.response.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class ClearHandler extends HandlerGeneric implements HttpHandler {
    /**
     * Handles the httpExchange for clearing the database
     * httpExchange holds onto the request and response object
     * @param httpExchange
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        boolean success = false;
        Clear clearService = new Clear();
        ClearResponse responseObj = new ClearResponse();
        String JSONString = "";

        try {
            responseObj = clearService.execute();                                           // this will actually give me a clear response object which I will need to return later
            JSONString = serialize(responseObj);

            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); // this indicates the sending proceedure is about to start
            OutputStream responseBody = httpExchange.getResponseBody();
            writeString(JSONString, responseBody);
            responseBody.close();                                                           // indicates "I'm done"
        }
        catch (IOException e) {
            // This response only gets sent if the request totally fails
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            httpExchange.getResponseBody().close();     // closes the connection without sending anything
            e.printStackTrace();
        }
    }
}

