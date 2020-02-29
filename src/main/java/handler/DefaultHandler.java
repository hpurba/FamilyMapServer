package handler;

// grabs the files for the website

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.*;
import java.net.*;
import java.nio.file.Paths;

import com.sun.net.httpserver.*;

public class DefaultHandler implements HttpHandler {
    /*
    // Handles HTTP requests containing the "/games/list" URL path.
    // The "exchange" parameter is an HttpExchange object, which is
    // defined by Java.
    // In this context, an "exchange" is an HTTP request/response pair
    // (i.e., the client and server exchange a request and response).
    // The HttpExchange object gives the handler access to all of the
    // details of the HTTP request (Request type [GET or POST],
    // request headers, request body, etc.).
    // The HttpExchange object also gives the handler the ability
    // to construct an HTTP response and send it back to the client
    // (Status code, headers, response body, etc.).
     */

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            String path;
            String URI = httpExchange.getRequestURI().toString();
            if(URI.equals("/")) {
//                path = "C:/Users/hikarupurba/Desktop/GIT_Repos/FamilyMapServer/web/index.html";
                path = "web/index.html";  // Path on my laptop
            }
            else {
                path = "web" + httpExchange.getRequestURI();
            }


            Path source = Paths.get(path);

            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream responseBody = httpExchange.getResponseBody();
            Files.copy(source, responseBody);
            responseBody.flush();   // forces to send.
            responseBody.close();
        }
        catch (IOException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            httpExchange.getResponseBody().close();
            e.printStackTrace();
        }


    }
}


// /Users/hikarupurba/Desktop/GIT_Repos/FamilyMapServer/web/index.html