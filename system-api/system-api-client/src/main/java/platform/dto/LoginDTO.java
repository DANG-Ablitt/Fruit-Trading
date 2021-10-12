package platform.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * 登录表单
 */
@Data
public class LoginDTO {
    /**
     * 手机号
     */
    private String mobile;

    /**
     * 短信验证码
     */
    private String captcha;

}