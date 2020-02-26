package model;

public class User {
    // Variables
    private String userName;    // User name, Non-empty String
    private String password;    // User's password, Non-empty String
    private String email;       // User's email address, Non-empty String
    private String firstName;   // User's first name, Non-empty String
    private String lastName;    // User's last name, Non-empty String
    private String gender;      // String: "f" or "m", should I make this char instead?
    private String personID;    // Unique person ID assigned to this user's generated model.Person object

    public User(String userName, String password, String email, String firstName, String lastName, String gender, String personID) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    // Functions    (setters and getters)

    // userName
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName(){ return userName; }

    // password
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword(){ return password; }

    // email
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail(){ return email; }

    // firstName
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName(){ return firstName; }

    // lastName
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getLastName(){ return lastName; }

    // gender
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getGender(){ return gender; }

    // personID
    public void setPersonID(String personID) {
        this.personID = personID;
    }
    public String getPersonID(){ return personID; }


    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof User) {
            User oUser = (User) o;
            return oUser.getUserName().equals(getUserName()) &&
                    oUser.getPassword().equals(getPassword()) &&
                    oUser.getEmail().equals(getEmail()) &&
                    oUser.getFirstName().equals(getFirstName()) &&
                    oUser.getLastName().equals(getLastName()) &&
                    oUser.getGender().equals(getGender()) &&
                    oUser.getPersonID().equals(getPersonID());
        } else {
            return false;
        }
    }
}