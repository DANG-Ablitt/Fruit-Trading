package platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 定时任务
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"redis","platform","log","security","utils","mybatis_plus","aspect","annotation"})
public class JobApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
    }

}