package jared.cheng.resource;

import jared.cheng.service.UserScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jared
 * @since 2023/3/12
 */
@RestController
@RequestMapping("/user-score")
public class UserScoreResource {

    @Autowired
    private UserScoreService svc;

    @PostMapping(path = "/add")
    public ResponseEntity<UserResponse> addScore(@RequestBody UserRequest request) {
        try {
            final UserResponse resp = svc.addScore(request);
            if (null == resp.getError()) {
                return new ResponseEntity<>(resp, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new UserResponse() {
                {
                    this.setError(new ErrorResponse());
                    this.getError().setErrorId(504);
                    this.getError().setErrorMessage("failed to add score");
                }
            }, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
