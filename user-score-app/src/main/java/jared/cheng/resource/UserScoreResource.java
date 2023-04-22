package jared.cheng.resource;

import jared.cheng.service.UserScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author jared
 * @since 2023/3/12
 */
@RestController
@RequestMapping("/user-score")
@RefreshScope
public class UserScoreResource {

    @Autowired
    private UserScoreService svc;

    @Value("${app.owner.name}")
    private String owner;

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
    @PostMapping(path = "/async-add")
    public ResponseEntity<AsyncUserResponse> addScore(@RequestBody AsyncUserRequest request) {
        final AsyncUserResponse resp = svc.addScore(request);
        if (null == resp.getError()) {
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/owner")
    public String getOwner() {
        return this.owner;
    }
}
