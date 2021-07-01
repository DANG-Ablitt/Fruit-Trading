package authentication.config.security;

import authentication.filter.authenticationprocessingfilter.NamePassLoginAuthenticationProcessingFilter;
import authentication.filter.authenticationprovider.NamePassLoginAuthenticationProvider;
import authentication.redis.CaptchaRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CaptchaRedis captchaRedis;
    @Bean
    public NamePassLoginAuthenticationProcessingFilter getSimpleAuthFilter() throws Exception {
        return new NamePassLoginAuthenticationProcessingFilter();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.authorizeRequests().antMatchers("/").permitAll();
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 前后端分离
                .and()
                .csrf().disable() // 禁用csrf
                .cors() // 跨域
                .and()
                .authorizeRequests()
                .antMatchers("/login1","/image_captcha","/QR_code").permitAll() // 匿名通过认证
                .anyRequest().authenticated() //剩下的任何请求都需要认证
                .and()
                .addFilterBefore(getSimpleAuthFilter(), UsernamePasswordAuthenticationFilter.class);


    }

    /**
     * Access-Control-Allow-Origin : http://localhost:3000
     * Access-Control-Allow-Credentials : true
     * Access-Control-Allow-Methods : GET, POST, OPTIONS
     * Access-Control-Allow-Headers : Origin, Content-Type, Accept
     */
    /**
     * 配置跨域
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:8081"); // 根据实际的需要去设置
        configuration.addAllowedMethod("*"); // 同上
        configuration.addAllowedHeader("*");
        configuration.setMaxAge(3600L);
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }



}
