package jared.cheng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.endpoint.EndpointAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author jared
 * @since 2023/3/12
 * @see <a href="https://www.eolink.com/news/post/20035.html">About ClassNotFundException: EndpointAutoConfiguration</a>
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ConditionalOnClass({EndpointAutoConfiguration.class})
public class UserScoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserScoreApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
