package platform.feign;

import constant.ServiceConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import platform.feign.fallback.MessageFeignClientFallback;
import utils.Result;

/**
 * 短信接口
 */
@Component
@FeignClient(name = ServiceConstant.RENREN_ADMIN_SERVER, fallback = MessageFeignClientFallback.class)
public interface MessageFeignClient {

    /**
     * 发送短信
     *
     * 可能会出现以下异常：Method has too many Body parameters
     * 异常原因：当使用Feign时，如果发送的是get请求，那么需要在请求参数前加上@RequestParam注解修饰，Controller里面可以不加该注解修饰
     * 正确写法参考：
     * @RequestMapping(value="/test", method=RequestMethod.GET)
     * Model test(@RequestParam("name") final String name,@RequestParam("age")  final int age);
     * feign中你可以有多个@RequestParam，但只能有不超过一个@RequestBody
     */
    @PostMapping("/message/sms/send")
    public Result send(@RequestParam("mobile")String mobile, @RequestParam("params")String params);
}
