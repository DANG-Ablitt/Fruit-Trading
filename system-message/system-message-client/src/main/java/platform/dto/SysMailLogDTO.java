package platform.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 邮件发送记录
 */
@Data
public class SysMailLogDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     * 邮件模板ID
     */
    private Long templateId;
    /**
     * 发送者
     */
    private String mailFrom;
    /**
     * 收件人
     */
    private String mailTo;
    /**
     * 抄送者
     */
    private String mailCc;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件正文
     */
    private String content;
    /**
     * 发送状态
     * 0：失败
     * 1：成功
     */
    private Integer status;
    /**
     * 创建时间
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createDate;

}
