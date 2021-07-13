package platform.feign;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import platform.dto.UploadDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import utils.Result;

/**
 * OSS
 */
@FeignClient(name = "shop-oss-server", configuration = OssFeignClient.MultipartSupportConfig.class)
public interface OssFeignClient {
    /**
     * 文件上传
     * @param file 文件
     * @return  返回路径
     */
    @PostMapping(value = "oss/upload")
    Result<UploadDTO> upload(@RequestPart("file") MultipartFile file);

    class MultipartSupportConfig {
        @Bean
        public Encoder feignFormEncoder() {
            return new SpringFormEncoder();
        }
    }

}