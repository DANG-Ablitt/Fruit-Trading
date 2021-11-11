package platform.feign;

import constant.ServiceConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import platform.feign.fallback.JobFeignClientFallback;
import utils.Result;

/**
 * 消息中间件接口
 */
@FeignClient(name = ServiceConstant.RENREN_JOB_SERVER, fallback = JobFeignClientFallback.class)
public interface JopFeignClient {
    /**
     * 保存定时任务
     */
    @PostMapping("job/schedule")
    Result save();
}