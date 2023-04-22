package jared.cheng.service;

import jared.cheng.client.UserReadClient;
import jared.cheng.client.UserWriteClient;
import jared.cheng.resource.*;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
    private final StreamBridge bridge;

    public UserScoreService(RestTemplate restTemplate, UserReadClient userReadClient, UserWriteClient userWriteClient, StreamBridge bridge) {
        this.restTemplate = restTemplate;
        this.userReadClient = userReadClient;
        this.userWriteClient = userWriteClient;
        this.bridge = bridge;
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

    public AsyncUserResponse addScore(AsyncUserRequest request) {

        try {
            final List<UserRequest> input = request.getUsers();

            // add scores for all input users
            input.stream().forEach(u -> {
                final ResponseEntity<UserResponse> resp = userReadClient.getUser(u.getUserName());
                if (HttpStatus.OK == resp.getStatusCode()) {
                    final UserResponse user = resp.getBody();
                    if (null == user.getScore()) {
                        u.setScore(u.getScore());
                    } else {
                        u.setScore(u.getScore() + user.getScore());
                    }
                }
            });

            // send message to update user score
            bridge.send("userScoreTopic", input);
            return new AsyncUserResponse(){
                {
                    this.setMessage("ACK");
                }
            };
        } catch (Exception e) {
            return new AsyncUserResponse(){
                {
                    this.setMessage("FAILED");
                    this.setError(new ErrorResponse());
                    this.getError().setErrorId(504);
                    this.getError().setErrorMessage("failed to add score: " + e.getMessage());
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
