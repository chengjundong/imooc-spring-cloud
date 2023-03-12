package jared.cheng.exception;

/**
 * Exception for user application
 *
 * @author jared
 * @since 2023/3/12
 */
public class UserException extends RuntimeException{

    private final UserError error;

    public UserException(UserError error) {
        super(error.getErrorMessage());
        this.error = error;
    }

    public UserException(UserError error, Throwable rc) {
        super(error.getErrorMessage(), rc);
        this.error = error;
    }

    public UserError getError() {
        return error;
    }
}
