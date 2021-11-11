package platform.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.serializer.JsonRedisSerializer;

/**
 * redis 配置
 * 说明：该模块需要连接后台管理、秒杀服务、抢购服务的 redis
 * 需要构造 redis 的多数据源
 */
@Configuration
public class TwoRedisConfig {

    @Value("${spring.redis1.database}")
    private int dbIndex;

    @Value("${spring.redis1.host}")
    private String host;

    @Value("${spring.redis1.port}")
    private int port;

    @Value("${spring.redis1.password}")
    private String password;

    @Value("${spring.redis1.timeout}")
    private int timeout;


    /**
     * 配置redisTemplate 注入方式使用@Resource(name="") 方式注入
     */
    @Bean(name = "twoRedisTemplate")
    public RedisTemplate<String, Object> twoRedisTemplate() {
        // 构建 jedis 连接
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setDatabase(dbIndex);
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setPassword(password);
        jedisConnectionFactory.setTimeout(timeout);
        // 构建 RedisTemplate
        RedisTemplate<String, Object> redisTemplate1 = new RedisTemplate<>();
        redisTemplate1.setKeySerializer(new StringRedisSerializer());
        redisTemplate1.setValueSerializer(new JsonRedisSerializer<>(Object.class));
        redisTemplate1.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate1.setHashValueSerializer(new JsonRedisSerializer<>(Object.class));
        redisTemplate1.setConnectionFactory(jedisConnectionFactory);
        return redisTemplate1;
    }

}
