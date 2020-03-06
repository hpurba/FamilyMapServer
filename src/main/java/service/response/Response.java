package service.response;

/**
 * Other Response objects will extend from this as a sort of template.
 */
public class Response {
    boolean success;

    public boolean isSuccess(boolean b) {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
