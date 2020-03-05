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

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            String path;
            String URI = httpExchange.getRequestURI().toString();

            if(URI.equals("/")) {
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