package platform.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * 授权信息
 */
@Data
public class AuthorizationDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * token
     */
    private String token;
    /**
     * 过期时长，单位秒
     */
    private Integer expire;
}
