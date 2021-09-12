package platform.feign.fallback;

import org.springframework.stereotype.Component;
import platform.dto.ScheduleJobDTO;
import platform.feign.JopFeignClient;
import utils.Result;

/**
 * 定时任务接口 Fallback
 */
@Component
public class JobFeignClientFallback implements JopFeignClient {
    @Override
    public Result save(ScheduleJobDTO dto) {
        return new Result();
    }
}
