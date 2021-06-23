package authentication.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

/**
 * 临时授权信息
 */
@Data
@PropertySource("token.properties")
public class InterimAuthorizationDTO implements Serializable {
    /**
     * 临时token
     */
    private String interim_token;
    /**
     * 临时token过期时间
     */
    @Value("${interim_token_expire}")
    private Integer interim_token_expire;

}
