package ir.encoding.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringbootWithRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootWithRedisApplication.class, args);
    }

}
