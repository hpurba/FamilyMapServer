package service.request;

/**
 * Request Body:
 * {
 * "userName": "susan", // Non-empty string
 * "password": "mysecret" // Non-empty string
 * }
 * Errors: Request property missing or has invalid value, Internal server error
 */
public class LoginRequest {
    // Variables
    private String userName;    // non-empty string
    private String password;    // non-empty string

    // Getters
    public String getUserName() { return userName; }
    public String getPassword() { return password; }
}
