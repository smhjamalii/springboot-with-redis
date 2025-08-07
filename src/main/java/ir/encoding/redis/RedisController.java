package ir.encoding.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    @PostMapping
    public void set(String key, String value) {
        redisService.set(key, value);
    }

    @PostMapping("/set-with-timout")
    public void set(
            String key,
            String value,
            Long timeout) {
        redisService.set(key, value, timeout, TimeUnit.MINUTES);
    }

    @PostMapping("/set-if-exist")
    public Boolean setIfAlreadyExist(String key, String value) {
        return redisService.setIfAlreadyExist(key, value);
    }

    @PostMapping("/set-if-absent")
    public Boolean setIfAbsent(String key, String value){
        return redisService.setIfAbsent(key, value);
    }

    @GetMapping("/ttl")
    public Long getTTL(@RequestParam String key) {
        return redisService.getTtlInSeconds(key);
    }

    @GetMapping
    public String get(String key) {
        return redisService.get(key);
    }

}
