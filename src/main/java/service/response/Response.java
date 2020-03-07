package service.response;

/**
 * Other Response objects will extend from this as a sort of template.
 */
public class Response {
    String success;

    public String isSuccess(String success) {
        return success;
    }
    public void setSuccess(String success) {
        this.success = success;
    }
}
