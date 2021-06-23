package authentication.jwt;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

/**
 * Jwt
 */

@Data
@Configuration
public class JwtProperties {
    private String secret;
    private int expire;
}