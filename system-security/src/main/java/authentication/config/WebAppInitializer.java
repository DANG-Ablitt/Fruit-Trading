package authentication.config;


import authentication.config.security.WebSecurityConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

    //spring Ioc环境配置
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfig.class,WebSecurityConfig.class};
    }

    //dispatcherServlet环境配置
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMvcConfig.class};
    }

    //拦截请求配置
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/image_captcha","/QR_code"};
    }
}
