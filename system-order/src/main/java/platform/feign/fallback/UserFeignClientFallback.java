package platform.feign.fallback;

import org.springframework.stereotype.Component;
import platform.feign.UserFeignClient;
import utils.Result;

import java.util.Map;

/**
 * 查询用户接口 Fallback
 */
@Component
public class UserFeignClientFallback implements UserFeignClient {

    @Override
    public Result info_user_id(Map<String,String> params) {
        return null;
    }
}
