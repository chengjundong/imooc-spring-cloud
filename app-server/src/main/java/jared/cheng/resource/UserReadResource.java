package jared.cheng.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Resource of user read application
 *
 * @author jared
 * @since 2023/3/11
 */
@RestController
@RequestMapping("/user")
public class UserReadResource {

    @GetMapping(path = "/hello")
    public String hello() {
        return "Hello, SpringCloud";
    }
}
