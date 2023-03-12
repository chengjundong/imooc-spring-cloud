package jared.cheng;

import jared.cheng.data.UserDO;
import jared.cheng.data.RedisHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@SpringBootApplication
@EnableDiscoveryClient
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

	@Bean("redisDataQueue")
	BlockingQueue<UserDO> redisDataQueue() {
		return new ArrayBlockingQueue<>(100);
	}

	@Bean
	RedisHelper redisHelper() {
		final Jedis jedis = new Jedis("localhost", 6379);
		final RedisHelper redisHelper = new RedisHelper(jedis, redisDataQueue());

		// create a demo thread to run task
		final Thread t1 = new Thread(redisHelper);
		t1.setDaemon(true);
		t1.start();

		return redisHelper;
	}
}
