package jared.cheng.resource;

/**
 * @author jared
 * @since 2023/4/22
 */
public class AsyncUserResponse {

    private ErrorResponse error;

    private String message;

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
