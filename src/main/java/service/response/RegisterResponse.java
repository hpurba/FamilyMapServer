package service.response;

// registerResponse object. This object is the response to a register request. Therefore, this object needs to be initialized/setters

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
public class RegisterResponse extends Response {
    // Variables
    private String authToken;
    private String userName;
    private String personID;
    private String message;

    // Setters
    public void setAuthToken(String authToken) { this.authToken = authToken; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setPersonID(String personID) { this.personID = personID; }
    public void setMessage(String message) { this.message = message; }

    public String getAuthToken() {
        return authToken;
    }

    public String getUserName() {
        return userName;
    }

    public String getPersonID() {
        return personID;
    }

    public String getMessage() {
        return message;
    }
}