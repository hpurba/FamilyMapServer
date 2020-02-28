package service.response;


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
public class personIDResponse  extends Response{

    private String associatedUserName;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;
    private boolean success;

    private String message;

    public void setMessage(String message) { this.message = message; }
}
