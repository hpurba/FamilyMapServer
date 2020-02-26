package service.response;

/**
 * Success Response Body:
 * {
 * message:Successfully added X persons and Y events to the database.
 * success:true // Boolean identifier
 * }
 * Error Response Body:
 * {
 * message:Description of the error
 * success:false // Boolean identifier
 * }
 */
public class fillResponse {
    private String message;

    public void setMessage(String message) { this.message = message; }
}
