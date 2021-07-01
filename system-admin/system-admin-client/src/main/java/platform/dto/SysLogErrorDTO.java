package platform.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 异常日志
 */
@Data
public class SysLogErrorDTO implements Serializable {
    private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private Long id;
	/**
	 * 模块名称，如：sys
	 */
	private String module;
	/**
	 * 请求URI
	 */
	private String requestUri;
	/**
	 * 请求方式
	 */
	private String requestMethod;
	/**
	 * 请求参数
	 */
	private String requestParams;
	/**
	 * 用户代理
	 */
	private String userAgent;
	/**
	 * 操作IP
	 */
	private String ip;
	/**
	 * 异常信息
	 */
	private String errorInfo;
	/**
	 * 创建时间
	 */
	private Date createDate;

}