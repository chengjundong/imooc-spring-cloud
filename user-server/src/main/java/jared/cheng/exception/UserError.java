package jared.cheng.exception;

/**
 * @author jared
 * @since 2023/3/12
 */
public enum UserError {
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    USER_ALREADY_EXISTS(501, "User already exists"),
    USER_NOT_FOUND(502, "User not found"),
    ;


    private final int errorId;
    private final String errorMessage;

    UserError(int errorId, String errorMessage) {
        this.errorId = errorId;
        this.errorMessage = errorMessage;
    }

    public int getErrorId() {
        return errorId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
