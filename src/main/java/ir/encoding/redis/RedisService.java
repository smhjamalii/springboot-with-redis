package ir.encoding.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    private final Expiration EXPIRATION = Expiration.from(Duration.ofDays(1));
    /**
     * Insert a key/value into redis
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * Insert a key/value into redis which will be removed (expired) after timeout is passed
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     */
    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public Boolean setIfAlreadyExist(String key, String value){
        return redisTemplate.opsForValue().setIfPresent(key, value);
    }

    public Boolean setIfAbsent(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    public Long getTTL(String key) {
        return redisTemplate.getExpire(key);
    }

    public Long getTtlInSeconds(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public Long getTtlInMinutes(String key) {
        return redisTemplate.getExpire(key, TimeUnit.MINUTES);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}
