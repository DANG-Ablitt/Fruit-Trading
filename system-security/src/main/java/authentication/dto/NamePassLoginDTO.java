package authentication.dto;


import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import java.io.Serializable;
import java.util.Collection;

/**
 * 手机号+密码+图形验证码登录信息
 */
public class NamePassLoginDTO implements Authentication,Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户手机号
     */
    private String mobile;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 验证码
     * 手机端登录：使用短信验证码
     * 浏览器登录：使用图片验证码
     */
    private String captcha;
    /**
     * 唯一标识
     * 手机端登录：获取安卓手机序列号
     * 浏览器登录：生成随机的UUID
     */
    private String uuid;

    public NamePassLoginDTO(String mobile, String password, String captcha, String uuid) {
        this.mobile = mobile;
        this.password = password;
        this.captcha = captcha;
        this.uuid = uuid;
    }

    @Override
    public String getName() {
        return mobile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getDetails() {
        return captcha;
    }

    @Override
    public Object getPrincipal() {
        return uuid;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {

    }
}
