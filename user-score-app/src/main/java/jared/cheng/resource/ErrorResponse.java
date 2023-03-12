package jared.cheng.resource;

/**
 * @author jared
 * @since 2023/3/12
 */
public class ErrorResponse {

    private int errorId;
    private String errorMessage;

    public int getErrorId() {
        return errorId;
    }

    public void setErrorId(int errorId) {
        this.errorId = errorId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
