package handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.request.RegisterRequest;
import service.response.RegisterResponse;

import java.io.*;
import java.net.HttpURLConnection;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class RegisterHandler implements HttpHandler {

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

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        boolean success = false;
        // RegisterRequest registerRequestObj = new RegisterRequest();
        // RegisterResponse responseObj = new RegisterResponse();
        String JSONString = "";


        try {
            // Use httpExchange request body as parameters for creating a new user
            // probably want to make the request body into a user
            try{
                ObjectInputStream registerRequestIS = new ObjectInputStream(httpExchange.getRequestBody()); // grabs the httpExchange requestbody and turns it into an object input stream
                RegisterRequest registerRequestObj = (RegisterRequest) registerRequestIS.readObject();
//                 System.out.println("" + (String) registerRequestIS.readObject());
            }
            catch (ClassNotFoundException e){
                e.printStackTrace();
            }


//             RegisterRequest request = gson.fromJson(reqData, RegisterRequest.class);

            System.out.print("Hi maybe we are closer to getting the register working.");


            // Execute a register request which will return a registerResponse object


//            responseObj = registe.execute(); // this will actually give me a clear response object which I will need to return later
//            JSONString = serialize(responseObj);
//
//            responseObj = clearobj.execute(); // this will actually give me a clear response object which I will need to return later
//            JSONString = serialize(responseObj);


            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); // this indicates the sending proceedure is about to start
            OutputStream responseBody = httpExchange.getResponseBody();
            writeString(JSONString, responseBody);
            // responseBody.flush();   // forces to send.   // this is not necessary
            responseBody.close();   // indicates "I'm done"
        }
        catch (IOException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            httpExchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}



//Description: Creates a new user account, generates 4 generations of ancestor data for the new
//user, logs the user in, and returns an auth token.