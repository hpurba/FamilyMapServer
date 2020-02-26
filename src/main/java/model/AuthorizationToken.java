package model;

public class AuthorizationToken {
    private String token;           // generated unique "authorization token" string for the user
    private String userName;        // username associated with the token

    // Initializes model.AuthorizationToken
    public AuthorizationToken(String token, String userName){
        this.token = token;
        this.userName = userName;
    }

    // Getter functions
    public String getToken(){ return token; }
    public String getUsername() { return userName; }
}


// NOTES FROM MASON

// Every API has: Service, Request, Result
// Move result and request into the service package later


// THERE IS AN EASIER WAY TO MAKE THE GETTERS AND SETTERS
// alt insert, setter and getter, select the ones I want


// 4 tables: Authorization Token, Event, Person, Users

// This is how you should comment
/**
 *
 */
