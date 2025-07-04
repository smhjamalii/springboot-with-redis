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

        /**
         * if you forget to set sentinel username it will work with redis correctly,
         * and you won't get any error message until you deploy in a real k8s cluster!
         */
        sentinelConfig.setSentinelUsername(this.USERNAME);

        /**
         * if you forget to set sentinel password you will get this ambiguous error message:
         * io.lettuce.core.RedisCommandExecutionException: NOAUTH HELLO must be called with the client already authenticated,
         * otherwise the HELLO <proto> AUTH <user> <pass> option can be used to authenticate the client and select the RESP protocol version at the same time
         */
        sentinelConfig.setSentinelPassword(this.PASSWORD);

        return new LettuceConnectionFactory(sentinelConfig);
    }

}
