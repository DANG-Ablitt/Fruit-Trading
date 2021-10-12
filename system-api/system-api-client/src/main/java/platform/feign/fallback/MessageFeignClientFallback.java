package platform.feign.fallback;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import platform.feign.MessageFeignClient;
import security.user.StaffDetail;
import utils.Result;

import java.util.Map;

/**
 * 短信验证码发送接口 Fallback
 */
@Component
public class MessageFeignClientFallback implements MessageFeignClient {

    @Override
    public void captcha( Map<String,String> mobileparams) {
        //return new Result<>();
    }

}