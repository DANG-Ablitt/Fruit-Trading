package platform.feign;

import constant.ServiceConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import platform.dto.ScheduleJobDTO;
import platform.feign.fallback.RabbitMQFeignClientFallback;
import utils.Result;

import java.util.Map;

/**
 * 消息中间件接口
 */
@FeignClient(name = ServiceConstant.RENREN_RABBITMQ_SERVER, fallback = RabbitMQFeignClientFallback.class)
public interface RabbitMQFeignClient {
    /**
     * 商品秒杀
     */
    @PostMapping("/rabbitmq/shop/send")
    Result ShopCouponsend(@RequestParam Map<String, Object> params);
}
