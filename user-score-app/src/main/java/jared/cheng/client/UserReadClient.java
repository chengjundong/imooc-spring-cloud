package jared.cheng.client;

import jared.cheng.resource.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client for USER-READ service
 *
 * @author jared
 * @since 2023/4/1
 * @implSpec service ID is USER-READ
 */
@FeignClient("USER-READ")
public interface UserReadClient {

    @GetMapping("/user/{userName}")
    ResponseEntity<UserResponse> getUser(@PathVariable String userName);
}
