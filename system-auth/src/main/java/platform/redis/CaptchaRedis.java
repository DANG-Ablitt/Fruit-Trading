package platform.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.RedisKeys;
import redis.RedisUtils;

/**
 * 验证码Redis
 */
@Component
public class CaptchaRedis {
    /**
     * 验证码5分钟过期
     */
    private final static long EXPIRE = 60 * 5L;
    @Autowired
    private RedisUtils redisUtils;

    public void set(String uuid, String captcha){
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