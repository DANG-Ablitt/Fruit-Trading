package authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

@Configuration
//定义spring mvc扫描的包
@ComponentScan(value="authentication.*",includeFilters= {@ComponentScan.Filter(type= FilterType.ANNOTATION,value= Controller.class)})
//启动srping mvc配置
//@EnableWebMvc
public class SpringMvcConfig {
    /*
     * 通过注解@Bean初始化视图解析器
     * @return ViewResolver视图解析器
     */
    @Bean(name="internalResourceViewResolver")
    public ViewResolver initViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        //viewResolver.setPrefix("/WEB-INF/jsp");
        //viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    /*
     * 自定义适配器
     * 初始化RequestMappingHandlerAdapter,并加载Http的Json转换器
     * @return RequestMappingHandlerAdapter对象
     */
    @Bean(name="requestMappingHandlerAdapter")
    public HandlerAdapter initRequestMappingHandlerAdapter() {
        //RequestMappingHandlerAdapter
        RequestMappingHandlerAdapter rmha = new RequestMappingHandlerAdapter();
        //http json转换器
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        //MappingJackson2HttpMessageConverter接收json类型消息的转换
        MediaType mediaType = MediaType.APPLICATION_JSON_UTF8;
        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(mediaType);
        //加入转换器的支持类型
        jsonConverter.setSupportedMediaTypes(mediaTypes);
        //往适配器加入json转换器
        rmha.getMessageConverters().add(jsonConverter);
        return rmha;
    }

}
