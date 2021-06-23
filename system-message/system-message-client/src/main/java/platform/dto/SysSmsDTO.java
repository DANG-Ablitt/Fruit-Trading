package platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信
 */
@Data
public class SysSmsDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     * 平台类型
     */
    private Integer platform;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 参数1
     */
    private String params1;
    /**
     * 参数2
     */
    private String params2;
    /**
     * 参数3
     */
    private String params3;
    /**
     * 参数4
     */
    private String params4;
    /**
     * 发送状态
     * 0：失败
     * 1：成功
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createDate;

}
