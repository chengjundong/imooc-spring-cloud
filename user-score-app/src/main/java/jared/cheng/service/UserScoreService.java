package jared.cheng.service;

import jared.cheng.client.UserReadClient;
import jared.cheng.client.UserWriteClient;
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

    // service id of user read service is USER-READ
    private static final String GET_URL = "http://USER-READ/user/";
    // service id of user write service is USER-WRITE
    private static final String UPDATE_SCORE_URL = "http://USER-WRITE/user/score";
    private final RestTemplate restTemplate;
    private final UserReadClient userReadClient;
    private final UserWriteClient userWriteClient;

    public UserScoreService(RestTemplate restTemplate, UserReadClient userReadClient, UserWriteClient userWriteClient) {
        this.restTemplate = restTemplate;
        this.userReadClient = userReadClient;
        this.userWriteClient = userWriteClient;
    }

    public UserResponse addScore(UserRequest request) {

        final ResponseEntity<UserResponse> resp = userReadClient.getUser(request.getUserName());
        if (HttpStatus.OK == resp.getStatusCode()) {
            final UserResponse user = resp.getBody();
            if (null == user.getScore()) {
                user.setScore(request.getScore());
            } else {
                user.setScore(request.getScore() + user.getScore());
            }
//            restTemplate.put(UPDATE_SCORE_URL, toWriteRequest(user));
            this.userWriteClient.updateScore(toWriteRequest(user));
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
