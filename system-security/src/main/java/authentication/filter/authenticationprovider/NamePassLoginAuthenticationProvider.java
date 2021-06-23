package authentication.filter.authenticationprovider;

import authentication.filter.jwtservice.JwtService;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 手机号+密码+图形验证码登录处理器
 */
@Data
public class NamePassLoginAuthenticationProvider implements AuthenticationProvider {
    private JwtService jwtService;

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return this.getJwtService().loadUserByUsername(authentication);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
