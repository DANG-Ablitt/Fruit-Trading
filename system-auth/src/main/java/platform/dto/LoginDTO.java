package platform.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 登录信息
 */
@Data
public class LoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 验证码
     */
    private String captcha;
    /**
     * 唯一标识
     */
    private String uuid;
}
