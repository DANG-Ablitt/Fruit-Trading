package authentication.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import utils.SpringContextUtils;
import java.util.Date;

/**
 * Jwt工具类
 */
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    /**
     * 生成jwt token
     */
    public String generateToken(Long userId) {
        return Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setSubject(userId+"")
            .setIssuedAt(new Date())
            .setExpiration(DateTime.now().plusSeconds(SpringContextUtils.getBean(JwtProperties.class).getExpire()).toDate())
            .signWith(SignatureAlgorithm.HS512, SpringContextUtils.getBean(JwtProperties.class).getSecret())
            .compact();
    }

    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                .setSigningKey(SpringContextUtils.getBean(JwtProperties.class).getSecret())
                .parseClaimsJws(token)
                .getBody();
        }catch (Exception e){
            logger.debug("validate is token error, token = " + token, e);
            return null;
        }
    }

    /**
     * token是否过期
     * @return  true：过期
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}