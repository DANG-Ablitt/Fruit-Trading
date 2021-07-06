package platform.feign.fallback;

import org.springframework.stereotype.Component;
import platform.feign.ParamsFeignClient;

/**
 * 参数接口 Fallback
 */
@Component
public class ParamsFeignClientFallback implements ParamsFeignClient {

    @Override
    public String getValue(String paramCode) {
        return null;
    }

    @Override
    public void updateValueByCode(String paramCode, String paramValue) {

    }
}