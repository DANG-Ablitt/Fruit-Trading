package security.user;

import lombok.Data;
import java.io.Serializable;

/**
 * 登录用户信息
 */
@Data
public class UserDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    private Long id;
    /**
     * 用户昵称
     */
    private String realName;
    /**
     * 用户头像
     */
    private String headUrl;
    /**
     * 用户性别
     */
    private Integer gender;
    /**
     * 用户电子邮箱
     */
    private String email;
    /**
     * 用户手机号
     */
    private String mobile;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 账号状态   0：正常   1：封号
     */
    private int kill;

}