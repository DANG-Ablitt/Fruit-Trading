package authentication.dto;


import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

/**
 * 授权信息
 */

@Data
public class AuthorizationDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String token;
    /**
     *token过期时间
     */
    private Integer token_expire;
    private String refresh_token;
    /**
     *refresh_token过期时间
     */
    private Integer refresh_token_expire;

}
