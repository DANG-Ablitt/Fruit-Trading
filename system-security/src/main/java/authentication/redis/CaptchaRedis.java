package authentication.redis;

import authentication.redis.config.RedisKeys;
import authentication.redis.config.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * 验证码Redis
 */
@Component
public class CaptchaRedis {
    /**
     * 验证码5分钟过期(短信验证码和图片验证码)
     */
    private final static long EXPIRE = 60 * 5L;

    @Autowired
    private RedisUtils redisUtils;

    public void set(String uuid, String captcha){
        //RedisKeys.getLoginCaptchaKey是静态方法
        String key = RedisKeys.getLoginCaptchaKey(uuid);
        redisUtils.set(key, captcha, EXPIRE);
    }

    public String get(String uuid){
        String key = RedisKeys.getLoginCaptchaKey(uuid);
        String captcha = (String)redisUtils.get(key);
        //删除验证码
        if(captcha != null){
            redisUtils.delete(key);
        }
        return captcha;
    }
}