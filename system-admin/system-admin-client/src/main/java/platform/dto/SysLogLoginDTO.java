package platform.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 登录日志
 */
@Data
public class SysLogLoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private Long id;
	/**
	 * 用户操作
	 * 0：用户登录
	 * 1：用户退出
	 */
	private Integer operation;
	/**
	 * 状态
	 * 0：失败
	 * 1：成功
	 * 2：账号已锁定
	 */
	private Integer status;
	/**
	 * 用户代理
	 */
	private String userAgent;
	/**
	 * 操作IP
	 */
	private String ip;
	/**
	 * 用户名
	 */
	private String creatorName;
	/**
	 * 创建时间
	 */
	private Date createDate;
}
