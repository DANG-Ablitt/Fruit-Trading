package platform.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * 注册表单
 */
@Data
public class RegisterDTO {
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 密码
     */
    private String password;

}
