package platform.service.impl;

import com.google.code.kaptcha.Producer;
import platform.redis.CaptchaRedis;
import platform.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.awt.image.BufferedImage;

/**
 * 验证码
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {
    @Autowired
    private Producer producer;
    @Autowired
    private CaptchaRedis captchaRedis;

    @Override
    public BufferedImage create(String uuid) {
        //生成验证码
        String captcha = producer.createText();

        //保存验证码
        captchaRedis.set(uuid, captcha);

        return producer.createImage(captcha);
    }

    @Override
    public boolean validate(String uuid, String code) {
        String captcha = captchaRedis.get(uuid);

        //验证码是否正确
        if(code.equalsIgnoreCase(captcha)){
           return true;
        }

        return false;
    }
}