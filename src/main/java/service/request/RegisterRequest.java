package service.request;

// object for a request to register

/**
 * Errors: Request property missing or has invalid value, Username already taken by another user,
 * Internal server error
 */
public class RegisterRequest {
    // Variables
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;

    // Getter
    public String getUserName() { return userName; }

}
