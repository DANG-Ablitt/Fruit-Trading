package platform.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.redis.CaptchaRedis;
import platform.service.SysSmsService;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class CaptchaService {

    @Autowired
    private CaptchaRedis captchaRedis;
    @Autowired
    private SysSmsService sysSmsService;

    public String create(String mobile,String template){
        //生成验证码
        String captcha = createText();
        //保存验证码
        captchaRedis.set(mobile, captcha);
        //对参数进行封装
        Map<String,String> map = new HashMap<String,String>();
        map.put("captcha",captcha);
        map.put("time","5");
        map.put("template",template);
        String json= JSON.toJSONString(map);
        //请求发送短信
        sysSmsService.send(mobile, json);
        return captcha;
    }

    /**
     * 生成6位数字验证码
     */
    public String createText(){
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

}
