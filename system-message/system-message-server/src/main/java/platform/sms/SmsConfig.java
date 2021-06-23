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
     * 阿里云AccessKeyId
     */
    private String aliyunAccessKeyId;
    /**
     * 阿里云AccessKeySecret
     */
    private String aliyunAccessKeySecret;
    /**
     * 阿里云短信签名
     */
    private String aliyunSignName;
    /**
     * 阿里云短信模板
     */
    private String aliyunTemplateCode;
    /**
     * 腾讯云AppId
     */
    private Integer qcloudAppId;
    /**
     * 腾讯云AppKey
     */
    private String qcloudAppKey;
    /**
     * 腾讯云短信签名
     */
    private String qcloudSignName;
    /**
     * 腾讯云短信模板ID
     */
    private String qcloudTemplateId;
    /**
     * 短信宝账号
     */
    private String duanxinbaoName;
    /**
     * 短信宝密码
     */
    private String duanxinbaoPassword;
    /**
     * 短信宝短信签名
     */
    private String duanxinbaoSignName;
    /**
     * 短信宝短信模板
     */
    private String duanxinbaoTemplate;
}