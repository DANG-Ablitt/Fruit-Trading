package authentication.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Jwt
 */

@Data
@Component
//从配置文件中加载jwt消息
@PropertySource({"classpath:jwt.properties"})
public class JwtProperties {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expire}")
    private int expire;
}