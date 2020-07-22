package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.services.ClearService;
import service.response.*;

import java.io.IOException;
import java.io.OutputStream;
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
        ClearService clearService = new ClearService();
        ClearResponse responseObj = new ClearResponse();
        String JsonString = "";

        try {
            responseObj = clearService.execute();                                           // this will actually give me a clear response object which I will need to return later

            if(responseObj.getSuccess() == "true") {
                JsonString = serialize(responseObj);
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream responseBody = httpExchange.getResponseBody();
                writeString(JsonString, responseBody);
                responseBody.close();
            } else {
                JsonString = serialize(responseObj);
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                OutputStream responseBody = httpExchange.getResponseBody();
                writeString(JsonString, responseBody);
                responseBody.close();
            }

            // ORIGINAL CODE
//            JSONString = serialize(responseObj);
//            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); // this indicates the sending proceedure is about to start
//            OutputStream responseBody = httpExchange.getResponseBody();
//            writeString(JSONString, responseBody);
//            responseBody.close();                                                           // indicates "I'm done"
        }
        catch (IOException e) {
            JsonString = serialize(responseObj);
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            OutputStream responseBody = httpExchange.getResponseBody();
            writeString(JsonString, responseBody);
            responseBody.close();
            e.printStackTrace();

//            // This response only gets sent if the request totally fails
//            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//            httpExchange.getResponseBody().close();     // closes the connection without sending anything
//            e.printStackTrace();
        }
    }
}

