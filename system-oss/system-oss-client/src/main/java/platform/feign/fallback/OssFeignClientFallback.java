package platform.feign.fallback;

import platform.dto.UploadDTO;
import platform.feign.OssFeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import utils.Result;

/**
 * OSS Fallback
 */
@Component
public class OssFeignClientFallback implements OssFeignClient {

    @Override
    public Result<UploadDTO> upload(MultipartFile file) {
        return new Result<UploadDTO>().error();
    }
}