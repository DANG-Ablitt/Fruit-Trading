package platform.feign.fallback;

import org.springframework.stereotype.Component;
import platform.dto.ScheduleJobDTO;
import platform.feign.JopFeignClient;
import platform.feign.RabbitMQFeignClient;
import utils.Result;

import java.util.Map;

/**
 * 消息中间件接口 Fallback
 */
@Component
public class RabbitMQFeignClientFallback implements RabbitMQFeignClient {

    @Override
    public Result ShopCouponsend(Map<String, Object> params) {
        return null;
    }
}
