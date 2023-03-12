package jared.cheng.resource;

import jared.cheng.data.UserDO;
import jared.cheng.exception.UserError;
import jared.cheng.exception.UserException;
import jared.cheng.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Resource of user read application
 *
 * @author jared
 * @since 2023/3/11
 */
@RestController
@RequestMapping("/user")
public class UserResource {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        try {
            final UserDO user = userService.createUser(request);
            return new ResponseEntity(buildUserResp(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(buildErrorResponse(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/score", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> updateScore(@RequestBody UserRequest request) {
        try {
            final UserDO user = userService.updateScore(request);
            return new ResponseEntity(buildUserResp(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(buildErrorResponse(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private UserResponse buildUserResp(UserDO userDO) {
        final UserResponse result = new UserResponse();
        result.setUserName(userDO.getUserName());
        result.setScore(userDO.getScore());
        return result;
    }

    private UserResponse buildErrorResponse(Exception e) {
        if (e instanceof UserException) {
            UserException ue = UserException.class.cast(e);
            return new UserResponse() {
                {
                    this.setError(new ErrorResponse());
                    this.getError().setErrorId(ue.getError().getErrorId());
                    this.getError().setErrorMessage(ue.getMessage());
                }
            };
        } else {
            return new UserResponse() {
                {
                    this.setError(new ErrorResponse());
                    this.getError().setErrorId(UserError.INTERNAL_SERVER_ERROR.getErrorId());
                    this.getError().setErrorMessage(e.getMessage());
                }
            };
        }
    }
}
