package authentication.service;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import authentication.redis.CaptchaRedis;
import utils.SpringContextUtils;
import java.awt.image.BufferedImage;

/**
 * 验证码（短信验证码和图片验证码）
 */
@Service
public class CaptchaService {
    @Autowired
    private CaptchaRedis captchaRedis;
    @Autowired
    private Producer producer;

    /**
     * 处理图片验证码
     */
    public BufferedImage image_create(String uuid) {
        //生成验证码
        String captcha = producer.createText();
        //保存验证码
        captchaRedis.set(uuid, captcha);
        return producer.createImage(captcha);
    }

    /**
     * 处理短信验证码
     */
    public String message_create(String uuid){
        //生成验证码（调用短信发送接口）
        //String captcha=
        //保存验证码到redis
        //captchaRedis.set(uuid, captcha);
        //return captcha;
        return null;
    }

    /**
     * 从redis中获取验证码并效验验证码
     */
    public boolean validate(String uuid, String code) {
        System.out.println(SpringContextUtils.getBean(CaptchaRedis.class));
        String captcha = SpringContextUtils.getBean(CaptchaRedis.class).get(uuid);
        //验证码是否正确
        if(code.equalsIgnoreCase(captcha)){
            return true;
        }
        return false;
    }
}
