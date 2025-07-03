package ir.encoding.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * Insert a key/value into redis
     * @param key
     * @param value
     */
    public void put(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * Insert a key/value into redis which will be removed (expired) after timeout is passed
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     */
    public void put(String key, String value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}
