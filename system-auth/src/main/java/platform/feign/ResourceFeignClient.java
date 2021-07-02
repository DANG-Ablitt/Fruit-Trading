package platform.feign;

import constant.ServiceConstant;
import platform.feign.fallback.ResourceFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import security.bo.ResourceBO;
import java.util.List;


@FeignClient(name = ServiceConstant.RENREN_ADMIN_SERVER, fallback = ResourceFeignClientFallback.class)
public interface ResourceFeignClient {
    /**
     * 获取所有资源列表
     */
    @GetMapping("sys/resource/list")
    List<ResourceBO> list();
}