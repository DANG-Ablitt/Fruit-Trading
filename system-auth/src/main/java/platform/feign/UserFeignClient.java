package platform.feign;

import constant.ServiceConstant;
import platform.feign.fallback.UserFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import security.user.StaffDetail;
import utils.Result;

/**
 * 用户接口
 */
@FeignClient(name = ServiceConstant.RENREN_ADMIN_SERVER, fallback = UserFeignClientFallback.class)
public interface UserFeignClient {

    /**
     * 根据用户ID，获取用户信息
     */
    @GetMapping("sys/user/getById")
    Result<StaffDetail> getById(@RequestParam("id") Long id);

    /**
     * 根据用户名，获取用户信息
     * @param username  用户名
     */
    @GetMapping("sys/user/getByUsername")
    Result<StaffDetail> getByUsername(@RequestParam("username") String username);

}