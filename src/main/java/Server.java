import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import handler.*;



public class Server {
    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer server;

    public static void main(String[] args) {
        String portNumber = "8080";
        new Server().run(portNumber);
    }

    private void run(String portNumber) {
        try {
            server = HttpServer.create( new InetSocketAddress(Integer.parseInt(portNumber)), MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        server.setExecutor(null);

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

        server.start();
        System.out.println("Server started");
    }
}

//
//
//public class Server {
//    public final int PORTNUM = 8080;
//
//    public static void main(String args[]) {
//        try {
//            new Server().run();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void run() throws IOException {
//        InetSocketAddress serverAddress = new InetSocketAddress(PORTNUM);
//        HttpServer server = HttpServer.create(serverAddress, 12);
//
//        server.setExecutor(null);
//        registerHandlerFiles(server);
//        server.start();
//        System.out.println("Server started");
//    }
//
//    public void registerHandlerFiles(HttpServer server) {
//        server.createContext("/", new DefaultHandler());                    // Default Handler
//        server.createContext("/user/register", new RegisterHandler());      // Creates a new user account, generates 4 generations of ancestor data for the new user, logs the user in, and returns an auth token.
//        server.createContext("/user/login", new LoginHandler());            // Logs in the user and returns an auth token
//        server.createContext("/clear", new ClearHandler());                 // Deletes ALL data from the database, including user accounts, auth tokens, and generated person and event data.
//        server.createContext("/fill", new FillHandler());                   // /fill/[username]/{generations} // Populates the server's database with generated data for the specified user name.
//        // server.createContext("/load", new LoadHandler());                   // Clears all data from the database (just like the /clear API), and then loads the posted user, person, and event data into the database.
//        // server.createContext("/person/", new PersonIDHandler());            // /person/[personID] // Returns the single Person object with the specified ID
//        // server.createContext("/person", new PersonHandler());               //  Returns ALL family members of the current user. The current user is determined from the provided auth token
//        // server.createContext("/event/", new EventIDHandler());              // /event/[eventID] //  Returns the single Event object with the specified ID.
//        // server.createContext("/event", new EventHandler());                 // Returns ALL events for ALL family members of the current user. The current user is determined from the provided auth token.
//    }
//}