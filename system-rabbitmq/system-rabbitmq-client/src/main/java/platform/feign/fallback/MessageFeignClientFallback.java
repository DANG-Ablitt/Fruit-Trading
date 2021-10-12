package platform.feign.fallback;

import platform.feign.MessageFeignClient;
import utils.Result;

public class MessageFeignClientFallback implements MessageFeignClient {
    @Override
    public Result send(String mobile, String params) {
        return new Result<>();
    }
}
