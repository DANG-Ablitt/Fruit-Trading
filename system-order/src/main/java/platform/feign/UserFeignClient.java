package platform.feign;

import constant.ServiceConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import platform.feign.fallback.UserFeignClientFallback;
import utils.Result;

import java.util.Map;

/**
 * 查询用户接口
 */
@FeignClient(name = ServiceConstant.RENREN_API_SERVER, fallback = UserFeignClientFallback.class)
public interface UserFeignClient {
    /**
     * 通过用户id查询用户信息
     */
    @PostMapping("api/auth/info_user_id")
    Result info_user_id(Map<String,String> params);
}
