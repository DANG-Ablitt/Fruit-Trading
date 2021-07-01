package platform.dto;

import lombok.Data;
import java.io.Serializable;
/**
 * 修改密码
 */
@Data
public class PasswordDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 原密码
     */
    private String password;
    /**
     * 新密码
     */
    private String newPassword;

}