package platform.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 支付宝支付配置
 */
@Configuration
public class AliPayConfig {

    /**
     * 应用ID
     */
    @Value("${pay.ali.appid}")
    private String appid;
    /**
     * 商户的私钥
     */
    @Value("${pay.ali.privateKey}")
    private String privateKey;
    /**
     * 支付回调地址
     */
    @Value("${pay.ali.notifyURL}")
    private String notifyURL;
    /**
     * 支付宝网关
     */
    @Value("${pay.ali.gateway}")
    private static String gateway;
    /**
     * 支付宝的公钥
     */
    @Value("${pay.ali.aliPublicKey:}")
    private static String aliPublicKey;

    @Bean
    public AlipayClient alipayClient(){
        return new DefaultAlipayClient(gateway,appid,privateKey,"json","GBK",aliPublicKey,"RSA2");
    }
}