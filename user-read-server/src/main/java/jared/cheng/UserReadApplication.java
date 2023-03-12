package jared.cheng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;

/**
 * @author jared
 * @since 2023/3/12
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UserReadApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserReadApplication.class, args);
    }

    @Bean
    Jedis jedis() {
        return new Jedis("localhost", 6379);
    }
}
