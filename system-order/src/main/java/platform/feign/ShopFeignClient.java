package platform.feign;

import constant.ServiceConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import platform.feign.fallback.ShopFeignClientFallback;
import platform.feign.fallback.UserFeignClientFallback;
import utils.Result;

import java.util.Map;

/**
 * 查询商品接口
 */
@FeignClient(name = ServiceConstant.RENREN_SHOP_SERVER, fallback = ShopFeignClientFallback.class)
public interface ShopFeignClient {
    /**
     * 根据商品id查询指定商品
     */
    @PostMapping("shop/coupon/coupon_info_id")
    Result coupon_info_id(Map<String,String> params);
}
