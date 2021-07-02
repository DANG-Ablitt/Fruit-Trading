package platform.feign;

import constant.ServiceConstant;
import platform.feign.fallback.ResourceFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import security.user.StaffDetail;
import utils.Result;

/**
 * 资源接口

 */
@FeignClient(name = ServiceConstant.RENREN_AUTH_SERVER, fallback = ResourceFeignClientFallback.class)
public interface ResourceFeignClient {

    /**
     * 是否有资源访问权限
     * @param token    token
     * @param url     资源URL
     * @param method  请求方式
     *
     * @return 有访问权限，则返回用户信息
     */
    @PostMapping("auth/resource")
    Result<StaffDetail> resource(@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language, @RequestParam("token") String token,
                                 @RequestParam("url") String url, @RequestParam("method") String method);
}