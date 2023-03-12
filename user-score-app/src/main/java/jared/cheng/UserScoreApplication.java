package jared.cheng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jared
 * @since 2023/3/12
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UserScoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserScoreApplication.class, args);
    }
}
