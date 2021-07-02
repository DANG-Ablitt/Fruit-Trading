package platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 授权服务
 */
//说明：redis的核心配置在system-tools模块的redis包里边
//spring boot扫描不到，所有需要scanBasePackages里加入对应的包
//否则会报错，但也必须把其他需要扫描的包一起加入（自定义）
@SpringBootApplication(scanBasePackages={"redis","platform","log"})
@EnableDiscoveryClient
@EnableFeignClients
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
