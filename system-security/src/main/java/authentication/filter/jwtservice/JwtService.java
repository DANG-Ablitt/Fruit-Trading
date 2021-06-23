package authentication.filter.jwtservice;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface JwtService {

    public Authentication loadUserByUsername(Authentication authentication) throws UsernameNotFoundException, Exception;
}
