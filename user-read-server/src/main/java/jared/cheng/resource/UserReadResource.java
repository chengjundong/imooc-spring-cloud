package jared.cheng.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

/**
 * Resource of user read application
 *
 * @author jared
 * @since 2023/3/11
 */
@RestController
@RequestMapping("/user")
public class UserReadResource {

    private static final String KEY = "user-service";
    @Autowired
    private Jedis jedis;
    private final ObjectMapper jackson = new ObjectMapper();

    @GetMapping(path = "/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> findByUserName(@PathVariable String userName) {
        try {
            final String result = jedis.hget(KEY, userName);
            return new ResponseEntity<>(jackson.readValue(result, UserResponse.class), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new UserResponse() {
                {
                    this.setError(new ErrorResponse());
                    this.getError().setErrorId(501);
                    this.getError().setErrorMessage("User Not Found");
                }
            },
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
