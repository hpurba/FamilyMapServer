import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import handler.*;

public class Server {

    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer server;

    /**
     * "main" method for the server program
     * "args" should contain one command-line argument, which is the port number on which the server should
     * accept incoming client connections.
     * @param args main_arguments
     */
    public static void main(String[] args) {
        String portNumber = args[0];    // port 8000, or 8080
        // portNumber = 8080;
        new Server().run("8080");
    }

    private void run(String portNumber) {

        System.out.println("Initializing HTTP Server");
        try {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);
        System.out.println("Creating contexts");
        registerHandlerFiles();
        System.out.println("Starting server");
        server.start();
        System.out.println("Server started");
    }

    /**
     * This will direct the program to the correct handler
     */
    public void registerHandlerFiles() {
        server.createContext("/", new DefaultHandler());                    // Default Handler
        server.createContext("/user/register", new RegisterHandler());      // Creates a new user account, generates 4 generations of ancestor data for the new user, logs the user in, and returns an auth token.
        server.createContext("/user/login", new LoginHandler());            // Logs in the user and returns an auth token
        server.createContext("/clear", new ClearHandler());                 // Deletes ALL data from the database, including user accounts, auth tokens, and generated person and event data.
        server.createContext("/fill", new FillHandler());                   // /fill/[username]/{generations} // Populates the server's database with generated data for the specified user name.
        // server.createContext("/load", new LoadHandler());                   // Clears all data from the database (just like the /clear API), and then loads the posted user, person, and event data into the database.
        // server.createContext("/person/", new PersonIDHandler());            // /person/[personID] // Returns the single Person object with the specified ID
        // server.createContext("/person", new PersonHandler());               //  Returns ALL family members of the current user. The current user is determined from the provided auth token
        // server.createContext("/event/", new EventIDHandler());              // /event/[eventID] //  Returns the single Event object with the specified ID.
        // server.createContext("/event", new EventHandler());                 // Returns ALL events for ALL family members of the current user. The current user is determined from the provided auth token.
    }
}