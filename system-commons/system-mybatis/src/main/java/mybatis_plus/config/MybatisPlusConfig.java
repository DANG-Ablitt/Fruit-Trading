package mybatis_plus.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import mybatis_plus.interceptor.DataFilterInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * mybatis-plus配置
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 配置数据权限
     */
    @Bean
    @Order(1)
    public DataFilterInterceptor dataFilterInterceptor() {
        return new DataFilterInterceptor();
    }

    /**
     * 配置分页
     */
    @Bean
    @Order(0)
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}