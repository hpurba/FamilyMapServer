package handler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import service.response.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.services.Clear;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class ClearHandler implements HttpHandler {
    /**
     * This makes any object a Json String
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

    // httpExchange holds onto the request and response object
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        boolean success = false;
        Clear clearobj = new Clear();
        ClearResponse responseObj = new ClearResponse();
        String JSONString = "";

        try {
            responseObj = clearobj.execute(); // this will actually give me a clear response object which I will need to return later
            JSONString = serialize(responseObj);


            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); // this indicates the sending proceedure is about to start
            OutputStream responseBody = httpExchange.getResponseBody();
            writeString(JSONString, responseBody);
            // responseBody.flush();   // forces to send.   // this is not necessary
            responseBody.close();   // indicates "I'm done"
        }
        catch (IOException e) {
            // This response only gets sent if the request totally fails
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            httpExchange.getResponseBody().close(); // closes the connection without sending anything
            e.printStackTrace();
        }
    }
}
