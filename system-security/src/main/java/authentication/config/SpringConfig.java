package authentication.config;

import authentication.filter.authenticationprovider.NamePassLoginAuthenticationProvider;
import authentication.redis.config.JsonRedisSerializer;
import authentication.redis.config.RedisParameter;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import redis.clients.jedis.JedisPoolConfig;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import utils.SpringContextUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import static com.google.common.collect.Lists.newArrayList;

/**
 * Spring配置文件
 */
@Configuration
@ComponentScan
//发现数据库配置文件
@PropertySource({"classpath:jdbc.properties"})
//发现映射器
@MapperScan(basePackages="authentication.dao")
public class SpringConfig {
    @Autowired
    private RedisParameter redisparameter;
    /*
     * 绑定资源属性
     */
    @Value("${jdbc.driver}")
    private String driverClass;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String userName;
    @Value("${jdbc.password}")
    private String passWord;

    /**
     * 配置验证码
     */
    @Bean
    public DefaultKaptcha producer() {
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.textproducer.font.color", "black");
        properties.put("kaptcha.textproducer.font.names", "Arial,Courier,cmr10,宋体,楷体,微软雅黑");
        properties.put("kaptcha.textproducer.char.space", "5");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

    /**
     * 配置swagger2
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //加了ApiOperation注解的类，才生成接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //包下的类，才生成接口文档
                //.apis(RequestHandlerSelectors.basePackage("io.renren.controller"))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(java.util.Date.class, String.class)
                .securitySchemes(security());

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("小型水果交易平台")
                .description("身份认证模块")
                .termsOfServiceUrl("https://www.renren.io/community")
                .version("1.1.0")
                .build();
    }

    private List<ApiKey> security() {
        return newArrayList(
                //new ApiKey(Constant.TOKEN_HEADER, Constant.TOKEN_HEADER, "header")
        );
    }

    /**
     * 配置MyBatis
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        //factoryBean.setConfigLocation(new String[]{"classpath:mybatis-config.xml"});
        // 添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        return factoryBean.getObject();
    }

    /**
     * 配置数据源
     */
    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(passWord);
        return dataSource;
    }

    /**
     * 配置 Jedis 连接池
     */
    @Bean
    public JedisPoolConfig redispoolconfig(){
        JedisPoolConfig PoolConfig=new JedisPoolConfig();
        PoolConfig.setMaxTotal(redisparameter.getMaxTotal());
        PoolConfig.setMaxIdle(redisparameter.getMaxIdle());
        PoolConfig.setMinIdle(redisparameter.getMinIdle());
        PoolConfig.setTestOnBorrow(redisparameter.getTestOnBorrow());
        return PoolConfig;
    }

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory jedisConnection=new JedisConnectionFactory();
        jedisConnection.setHostName(redisparameter.getHost());
        jedisConnection.setPort(redisparameter.getPort());
        jedisConnection.setPassword(redisparameter.getPassword());
        jedisConnection.setPoolConfig(redispoolconfig());
        //暂时不开启哨兵模式
        return jedisConnection;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JsonRedisSerializer<Object>(Object.class));
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JsonRedisSerializer<Object>(Object.class));
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

    /**
     *注意：必须在这里配置
     * 否则spring无法自动调用setApplicationContext
     * 使用时会出现无法获取applicationContext，并抛出NullPointerException
     */
    @Bean
    public SpringContextUtils springContextsUtil(){
        return new SpringContextUtils();
    }

}
