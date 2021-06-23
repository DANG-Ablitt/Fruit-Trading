package authentication.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * 手机号+数字验证码登录信息
 */
@Data
public class NameCodeLoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户手机号
     */
    private String mobile;
    /**
     * 验证码
     */
    private String captcha;
    /**
     * 唯一标识
     * 手机端登录：获取安卓手机序列号
     * 浏览器登录：生成随机的UUID
     */
    private String uuid;
}
