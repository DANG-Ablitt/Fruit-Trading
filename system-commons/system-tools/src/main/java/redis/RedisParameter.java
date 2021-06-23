package redis;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 从文件中获取redis.properties配置参数
 */
@Component
@Data
@PropertySource(value = "redis.properties")
public class RedisParameter {
    @Value("${redis.pool.maxTotal}")
    private Integer maxTotal;
    @Value("${redis.pool.maxIdle}")
    private Integer maxIdle;
    @Value("${redis.pool.minIdle}")
    private Integer minIdle;
    @Value("${redis.pool.testOnBorrow}")
    private Boolean testOnBorrow;
    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private int port;
    @Value("${redis.password}")
    private String password;
}
