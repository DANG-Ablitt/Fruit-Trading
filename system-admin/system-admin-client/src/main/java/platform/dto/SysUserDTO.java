package platform.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户管理
 */
@Data
public class SysUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private Long id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 姓名
	 */
	private String realName;
	/**
	 * 头像
	 */
	private String headUrl;
	/**
	 * 性别   0：男   1：女    2：保密
	 */
	private Integer gender;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 部门ID
	 */
	private Long deptId;
	/**
	 * 超级管理员   0：否   1：是
	 */
	private Integer superAdmin;
	/**
	 * 状态  0：停用    1：正常
	 */
	private Integer status;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 角色ID列表
	 */
	private List<Long> roleIdList;
	/**
	 * 部门名称
	 */
	private String deptName;

}