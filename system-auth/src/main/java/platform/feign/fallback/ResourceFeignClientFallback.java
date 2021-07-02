package platform.feign.fallback;

import platform.feign.ResourceFeignClient;
import org.springframework.stereotype.Component;
import security.bo.ResourceBO;
import java.util.ArrayList;
import java.util.List;

/**
 * 资源 Fallback
 */
@Component
public class ResourceFeignClientFallback implements ResourceFeignClient {

    @Override
    public List<ResourceBO> list() {
        return new ArrayList<>();
    }
}