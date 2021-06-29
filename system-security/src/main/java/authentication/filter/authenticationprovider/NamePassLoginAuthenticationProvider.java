package authentication.filter.authenticationprovider;

import authentication.dto.NamePassLoginDTO;
import authentication.filter.jwtservice.JwtService;
import authentication.filter.jwtservice.NamePassLogin;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 手机号+密码+图形验证码登录处理器
 */
@Data
@Component
public class NamePassLoginAuthenticationProvider implements AuthenticationProvider {
    private JwtService jwtService;

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        setJwtService(new NamePassLogin());
        return this.getJwtService().loadUserByUsername(authentication);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return NamePassLoginDTO.class.isAssignableFrom(aClass);
    }
}
