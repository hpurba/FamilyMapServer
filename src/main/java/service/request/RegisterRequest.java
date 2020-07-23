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
    public String getGender() { return gender; }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
