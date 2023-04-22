package jared.cheng.message;

import jared.cheng.resource.UserRequest;
import jared.cheng.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Consumer;

/**
 * Spring cloud stream
 *
 * @author jared
 * @since 2023/4/22
 */
@Configuration
public class StreamConfiguration {

    @Autowired
    private IUserService userService;

    @Bean
    public Consumer<List<UserRequest>> addScore() {
        return users -> users.forEach(u -> userService.updateScore(u));
    }
}
