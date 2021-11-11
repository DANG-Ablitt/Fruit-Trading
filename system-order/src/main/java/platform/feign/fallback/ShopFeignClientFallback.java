package platform.feign.fallback;

import org.springframework.stereotype.Component;
import platform.feign.ShopFeignClient;
import utils.Result;

import java.util.Map;

/**
 * 查询商品接口 Fallback
 */
@Component
public class ShopFeignClientFallback implements ShopFeignClient {

    @Override
    public Result coupon_info_id(Map<String,String> params) {
        return null;
    }
}
