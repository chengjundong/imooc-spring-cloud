package jared.cheng.service;

import jared.cheng.resource.ErrorResponse;
import jared.cheng.resource.UserRequest;
import jared.cheng.resource.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author jared
 * @since 2023/3/12
 */
@Service
public class UserScoreService {

    private static final String GET_URL = "http://localhost:9030/user/";
    private static final String UPDATE_SCORE_URL = "http://localhost:8080/user/score";
    private final RestTemplate rest = new RestTemplate();

    public UserResponse addScore(UserRequest request) {

        final ResponseEntity<UserResponse> resp = rest.getForEntity(GET_URL + request.getUserName(), UserResponse.class);
        if (HttpStatus.OK == resp.getStatusCode()) {
            final UserResponse user = resp.getBody();
            if (null == user.getScore()) {
                user.setScore(request.getScore());
            } else {
                user.setScore(request.getScore() + user.getScore());
            }
            rest.put(UPDATE_SCORE_URL, toWriteRequest(user));
            return user;
        } else {
            return new UserResponse() {
                {
                    this.setError(new ErrorResponse());
                    this.getError().setErrorId(504);
                    this.getError().setErrorMessage("failed to add score");
                }
            };
        }
    }

    private UserRequest toWriteRequest(UserResponse userResponse) {
        final UserRequest result = new UserRequest();
        result.setUserName(userResponse.getUserName());
        result.setScore(userResponse.getScore());
        return result;
    }
}
