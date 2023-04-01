package jared.cheng.client;

import jared.cheng.resource.UserRequest;
import jared.cheng.resource.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Feign client for USER-WRITE service
 *
 * @author jared
 * @since 2023/4/1
 * @implSpec service ID is USER-WRITE
 */
@FeignClient("USER-WRITE")
public interface UserWriteClient {

    @PutMapping("/user/score")
    ResponseEntity<UserResponse> updateScore(UserRequest requestBody);
}
