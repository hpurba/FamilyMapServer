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
