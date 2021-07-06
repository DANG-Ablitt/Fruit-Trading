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
     * 密码
     */
    private String password;

}