package ir.encoding.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    @PostMapping
    public void set(String key, String value) {
        redisService.put(key, value);
    }

    @PostMapping("/set-with-timout")
    public void set(String key, String value, long timeout) {
        redisService.put(key, value, timeout, TimeUnit.MINUTES);
    }

    @GetMapping
    public String get(String key) {
        return redisService.get(key);
    }

}
