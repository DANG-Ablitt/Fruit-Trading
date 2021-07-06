package platform.sms;

import lombok.Data;
import java.io.Serializable;

/**
 * 短信配置信息
 */
@Data
public class SmsConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 平台
     * 1：阿里云
     * 2：腾讯云
     * 3：短信宝
     */
    private Integer platform;
    /**
     * 短信发送平台账号
     */
    private String name;
    /**
     * 短信发送平台密码
     */
    private String Password;
    /**
     * 短信签名
     */
    private String signName;
    /**
     * 短信模板
     */
    private String template;
}