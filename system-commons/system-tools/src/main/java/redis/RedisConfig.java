package redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;
import redis.redisserializer.JsonRedisSerializer;

/**
 * Redis配置
 */

@Configuration
@ComponentScan
public class RedisConfig {
    @Autowired
    private RedisParameter redisparameter;

    /**
     * 配置 Jedis 连接池
     */
    @Bean
    public JedisPoolConfig redispoolconfig(){
        JedisPoolConfig PoolConfig=new JedisPoolConfig();
        PoolConfig.setMaxTotal(redisparameter.getMaxTotal());
        PoolConfig.setMaxIdle(redisparameter.getMaxIdle());
        PoolConfig.setMinIdle(redisparameter.getMinIdle());
        PoolConfig.setTestOnBorrow(redisparameter.getTestOnBorrow());
        return PoolConfig;
    }

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory jedisConnection=new JedisConnectionFactory();
        jedisConnection.setHostName(redisparameter.getHost());
        jedisConnection.setPort(redisparameter.getPort());
        jedisConnection.setPassword(redisparameter.getPassword());
        jedisConnection.setPoolConfig(redispoolconfig());
        //暂时不开启哨兵模式
        return jedisConnection;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JsonRedisSerializer<Object>(Object.class));
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JsonRedisSerializer<Object>(Object.class));
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}