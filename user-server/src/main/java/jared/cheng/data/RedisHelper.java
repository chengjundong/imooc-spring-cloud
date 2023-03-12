package jared.cheng.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.util.concurrent.BlockingQueue;

/**
 * Runnable task in backend to sync data from H2 to Redis, it takes data from data queue.
 *
 * @author jared
 * @since 2023/3/12
 */
public class RedisHelper implements Runnable{

    private static final String KEY = "user-service";
    private final Jedis jedis;
    private final BlockingQueue<UserDO> queue;
    private final ObjectMapper jackson = new ObjectMapper();

    public RedisHelper(Jedis jedis, BlockingQueue<UserDO> queue) {
        this.jedis = jedis;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                final UserDO user = queue.take();
                jedis.hset(KEY, user.getUserName(), jackson.writeValueAsString(user));
            } catch (Exception e) {
                // continue
            }
        }
    }
}
