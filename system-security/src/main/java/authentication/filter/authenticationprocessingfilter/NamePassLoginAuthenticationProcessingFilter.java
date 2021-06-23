package authentication.filter.authenticationprocessingfilter;

import authentication.dto.NamePassLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import authentication.redis.LoginRedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 手机号+密码+图形验证码登录过滤器
 */
public class NamePassLoginAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    @Autowired
    private LoginRedis loginRedis;

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "captcha";
    public static final String SPRING_SECURITY_FORM_UUID_KEY = "uuid";
    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
    private String captchaParameter=SPRING_SECURITY_FORM_CAPTCHA_KEY;
    private String uuidParameter=SPRING_SECURITY_FORM_UUID_KEY;

    /**
     * 通过构造器指定该登录的访问方式：
     * 例如：http://localhost:8080/login POST
     */
    public NamePassLoginAuthenticationProcessingFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        //判断请求是否为POST请求
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        //读取用户名（手机号）
        String username = request.getParameter(usernameParameter);
        //读取密码
        String password = request.getParameter(passwordParameter);
        //读取验证码
        String captcha = request.getParameter(captchaParameter);
        //读取uuid
        String uuid = request.getParameter(uuidParameter);
        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }
        if (captcha == null) {
            captcha = "";
        }
        if (uuid == null) {
            uuid = "";
        }
        username = username.trim();
        NamePassLoginDTO authRequest=null;
        //根据uuid检查数据是否在redis缓存中
        if((authRequest=(NamePassLoginDTO)loginRedis.get(uuid))==null){
            //参数封装并保存到redis
            authRequest=new NamePassLoginDTO(username,password,captcha,uuid);
            loginRedis.set(authRequest);
        }
        return this.getAuthenticationManager().authenticate(authRequest);
    }


}
