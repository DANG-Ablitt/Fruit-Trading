package authentication.filter.jwtservice;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class NameCodeLogin implements JwtService {

    /**
     *手机号+短信验证码登录详细认证过程
     */
    @Override
    public Authentication loadUserByUsername(Authentication authentication) throws UsernameNotFoundException {
        return null;

    }


}
