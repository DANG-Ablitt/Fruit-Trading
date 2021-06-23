package platform.email;

import lombok.Data;
import java.io.Serializable;

/**
 * 邮件配置信息
 */
@Data
public class EmailConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * STMP
     */
    private String smtp;
    /**
     * 端口号
     */
    private Integer port;
    /**
     * 邮箱账号
     */
    private String username;
    /**
     * 邮箱密码
     */
    private String password;
}