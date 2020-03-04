package handler;

import DAO.DataAccessException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.FillRequest;
import service.request.RegisterRequest;
import service.response.ClearResponse;
import service.response.FillResponse;
import service.response.RegisterResponse;
import service.services.Clear;
import service.services.Fill;
import service.services.Register;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.sql.SQLException;

public class FillHandler implements HttpHandler {

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
     * Handles the httpExchange. Populates the server's database with generated data
     * for the specified userName.
     * @param httpExchange
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        System.out.print("Now inside the fill handler");
        

//        try {
//            boolean success = false;
//            Fill fillService = new Fill();
//            FillResponse fillResponseObj = new FillResponse();
//            String JsonString = "";
//            Gson gson = new Gson();
//            int generations;
//
//            URI uri = httpExchange.getRequestURI();
//            String[] parts = uri.getPath().split("/");
//            String userName = parts[2];
//            if(parts.length == 3){
//                generations = 4;
//            }
//            else {
//                generations = Integer.parseInt(parts[3]);
//            }
//
////            // From httpExchange, grab the inputStream request Body as an inputStream
////            InputStream requestBodyIS = httpExchange.getRequestBody();
////            String reqJsonStr = readString(requestBodyIS); // Convert InputStream to a Json
////            FillRequest registerRequestObj = gson.fromJson(reqJsonStr, RegisterRequest.class); // Json String to the request Object
//
////            try{
////                registerResponseObj = fillService.execute(registerRequestObj);    // this will give back to me a response object
////            } catch (DataAccessException e) {
////                e.printStackTrace();
////            } catch (SQLException e) {
////                e.printStackTrace();
////            }
//
//
//
//            JsonString = serialize(fillResponseObj);    // object to Json String
//            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); // this indicates the sending proceedure is about to start
//            OutputStream responseBody = httpExchange.getResponseBody();
//            writeString(JsonString, responseBody);
//            responseBody.close();   // indicates "I'm done", closes the connection
//        }
//        catch (IOException e) {
//            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
//            httpExchange.getResponseBody().close();
//            e.printStackTrace();
//        }


    }
}
