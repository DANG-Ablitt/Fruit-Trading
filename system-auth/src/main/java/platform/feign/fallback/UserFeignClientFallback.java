package platform.feign.fallback;

import platform.feign.UserFeignClient;
import org.springframework.stereotype.Component;
import security.user.StaffDetail;
import utils.Result;

/**
 * 用户接口 Fallback
 */
@Component
public class UserFeignClientFallback implements UserFeignClient {

    @Override
    public Result<StaffDetail> getById(Long id) {
        return new Result<>();
    }

    @Override
    public Result<StaffDetail> getByUsername(String username) {
        return new Result<>();
    }
}