package platform.feign;

import constant.ServiceConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import platform.feign.fallback.ParamsFeignClientFallback;

/**
 * 参数接口
 * @since 1.1.0
 */
@FeignClient(name = ServiceConstant.RENREN_ADMIN_SERVER, fallback = ParamsFeignClientFallback.class)
public interface ParamsFeignClient {

    /**
     * 根据参数编码，获取参数值
     * @param paramCode  参数编码
     * @return  返回参数值
     */
    @GetMapping("sys/params/code/{paramCode}")
    String getValue(@PathVariable("paramCode") String paramCode);

    /**
     * 根据参数编码，更新参数值
     * @param paramCode  参数编码
     * @param paramValue  参数值
     */
    @PutMapping("sys/params/code/{paramCode}")
    void updateValueByCode(@PathVariable("paramCode") String paramCode, @RequestParam("paramValue") String paramValue);

}