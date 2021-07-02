package platform.feign.fallback;

import platform.feign.ResourceFeignClient;
import org.springframework.stereotype.Component;
import security.user.StaffDetail;
import utils.Result;

/**
 * 资源接口 Fallback
 *
 * @since 1.0.0
 */
@Component
public class ResourceFeignClientFallback implements ResourceFeignClient {

    @Override
    public Result<StaffDetail> resource(String language, String token, String url, String method) {
        return new Result<StaffDetail>().error();
    }
}