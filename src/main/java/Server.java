import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import handler.*;

/*
	This example demonstrates the basic structure of the Family Map Server
	(although it is for a fictitious "Ticket to Ride" game, not Family Map).
	The example is greatly simplfied to help you more easily understand the
	basic elements of the server.
	
	The Server class is the "main" class for the server (i.e., it contains the 
		"main" method for the server program).
	When the server runs, all command-line arguments are passed in to Server.main.
	For this server, the only command-line argument is the port number on which 
		the server should accept incoming client connections.
*/
public class Server {
    /*
    // The maximum number of waiting incoming connections to queue.
    // While this value is necessary, for our purposes it is unimportant.
    // Take CS 460 for a deeper understanding of what it means.
     */
    private static final int MAX_WAITING_CONNECTIONS = 12;

    /*
    // Java provides an HttpServer class that can be used to embed
    // an HTTP server in any Java program.
    // Using the HttpServer class, you can easily make a Java
    // program that can receive incoming HTTP requests, and respond
    // with appropriate HTTP responses.
    // HttpServer is the class that actually implements the HTTP network
    // protocol (be glad you don't have to).
    // The "server" field contains the HttpServer instance for this program,
    // which is initialized in the "run" method below.
     */
    private HttpServer server;

    /*
    // This method initializes and runs the server.
    // The "portNumber" parameter specifies the port number on which the
    // server should accept incoming client connections.
     */
    private void run(String portNumber) {
        /*
        // Since the server has no "user interface", it should display "log"
        // messages containing information about its internal activities.
        // This allows a system administrator (or you) to know what is happening
        // inside the server, which can be useful for diagnosing problems
        // that may occur.
         */
        System.out.println("Initializing HTTP Server");
        try {
            /*
            // Create a new HttpServer object.
            // Rather than calling "new" directly, we instead create
            // the object by calling the HttpServer.create static factory method.
            // Just like "new", this method returns a reference to the new object.
             */
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        /*
        // Indicate that we are using the default "executor".
        // This line is necessary, but its function is unimportant for our purposes.
         */
        server.setExecutor(null);
        /*
        // Log message indicating that the server is creating and installing
        // its HTTP handlers.
        // The HttpServer class listens for incoming HTTP requests.  When one
        // is received, it looks at the URL path inside the HTTP request, and
        // forwards the request to the handler for that URL path.
         */
        System.out.println("Creating contexts");
        /*
        // Create and install the HTTP handler for the "/_/_ " URL path.
        // When the HttpServer receives an HTTP request containing the
        // "/_/_ " URL path, it will forward the request to __Handler for processing.
         */
        registerHandlerFiles();
        System.out.println("Starting server");
        /*
        // Tells the HttpServer to start accepting incoming client connections.
        // This method call will return immediately, and the "main" method
        // for the program will also complete.
        // Even though the "main" method has completed, the program will continue
        // running because the HttpServer object we created is still running
        // in the background.
         */
        server.start();
        // Log message indicating that the server has successfully started.
        System.out.println("Server started");
    }

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

    // Register Handler Files
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