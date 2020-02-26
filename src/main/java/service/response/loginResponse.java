package service.response;

/**
 * Success Response Body:
 * {
 * authToken: cf7a368f, // Non-empty auth token string
 * userName: susan, // User name passed in with request
 * personID: 39f9fe46 // Non-empty string containing the Person ID of the
 * // user’s generated Person object
 * success:true // Boolean identifier
 * }
 * Error Response Body:
 * {
 * message: Description of the error
 * success:false // Boolean identifier
 * }
 */
public class loginResponse {

    private String authorizationToken;  // non-empty authorization token string
    private String userName;            // user name passed in with request
    private String personID;            // non-empty string containing the Person ID of the user’s generated Person object
    private String message;             // message to be returned

    // Setters
    public void setAuthorizationToken(String authorizationToken) { this.authorizationToken = authorizationToken; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setPersonID(String personID) { this.personID = personID; }

    // message setter and getter
    public void setMessage(String message) { this.message = message; }
    public String getMessage() { return message; }
}



