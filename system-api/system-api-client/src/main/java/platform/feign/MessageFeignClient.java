package platform.feign;

import constant.ServiceConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import platform.feign.fallback.MessageFeignClientFallback;
import security.user.StaffDetail;
import utils.Result;

import java.util.Map;

/**
 * 短信验证码发送接口
 */
@FeignClient(name = ServiceConstant.RENREN_MESSAGE_SERVER, fallback = MessageFeignClientFallback.class)
public interface MessageFeignClient {

    /**
     * 发送短信验证码
     */
    @PostMapping("message/sms/captcha")
    public void captcha(@RequestBody Map<String,String> mobileparams);
}