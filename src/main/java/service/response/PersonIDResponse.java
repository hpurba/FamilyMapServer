package service.response;


import com.google.gson.annotations.SerializedName;
import model.Event;
import model.Person;

/**
 * Success Response Body:
 * {
 * associatedUsername:susan, // Name of user account this person belongs to
 * personID: 7255e93e, // Person’s unique ID
 * firstName:Stuart, // Person’s first name
 * lastName:Klocke, // Person’s last name
 * gender:m, // Person’s gender (m or f)
 * fatherID:7255e93e // ID of person’s father [OPTIONAL, can be
 * missing]
 * motherID:d3gz214j // ID of person’s mother [OPTIONAL, can be missing]
 * spouseID:f42126c8 // ID of person’s spouse [OPTIONAL, can be missing]
 * success:true // Boolean identifier
 * }
 * Error Response Body:
 * {
 * message:Description of the error
 * success:false // Boolean identifier
 * }
 */
public class PersonIDResponse extends Response{
    private String personID;
    private String associatedUsername;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouse;
    private String message;

    private Person person;
    public void setPerson(Person person) { this.person = person; }
    public Person getPerson() { return person; }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
