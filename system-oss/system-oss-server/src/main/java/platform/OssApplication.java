/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * OSS模块
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.1.0
 */
@SpringBootApplication(scanBasePackages={"redis","platform","log","security","utils","mybatis_plus","aspect","annotation"} )
@EnableDiscoveryClient
@EnableFeignClients
public class OssApplication {

    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }

}
