package ir.encoding.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class RedisConfig {

    private final String MASTER_NODE_NAME;
    private final String SENTINEL_NODES;
    private final String USERNAME;
    private final String PASSWORD;

    public RedisConfig(
            @Value("${spring.data.redis.sentinel.master}") String masterNodeName,
            @Value("${spring.data.redis.sentinel.nodes}") String sentinelNodes,
            @Value("${spring.data.redis.username}") String username,
            @Value("${spring.data.redis.password}") String password) {

        MASTER_NODE_NAME = masterNodeName;
        SENTINEL_NODES = sentinelNodes;
        USERNAME = username;
        PASSWORD = password;
    }

    @Bean
    public RedisConnectionFactory lettuceConnectionFactory() {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master(this.MASTER_NODE_NAME)
                .sentinel(RedisNode.fromString(this.SENTINEL_NODES));

        sentinelConfig.setUsername(this.USERNAME);
        sentinelConfig.setPassword(this.PASSWORD);
        sentinelConfig.setSentinelUsername(this.USERNAME);
        sentinelConfig.setSentinelPassword(this.PASSWORD);

        return new LettuceConnectionFactory(sentinelConfig);
    }

}
