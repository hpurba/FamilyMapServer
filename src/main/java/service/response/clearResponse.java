package service.response;

/**
 * Success Response Body:
 * {
 * message: Clear succeeded.
 * success:true // Boolean identifier
 * }
 * Error Response Body:
 * {
 * message: Description of the error
 * 6
 * success:false // Boolean identifier
 * }
 */
public class clearResponse {
    private String message;

    public void setMessage(String message) { this.message = message; }
}
