package authentication.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

/**
 * 职员基本信息
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SysStaffEntity  {
	private static final long serialVersionUID = 1L;

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
	 * 性别   0：男   1：女
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
	 * 部门ID
	 */
	private Long deptId;
	/**
	 * 删除标识  0：未删除    1：删除
	 */
	private Integer delFlag;
	/**
	 * 更新者
	 */
	private Long updater;
	/**
	 * 更新时间
	 */
	private Date updateDate;

	/**
	 * 部门名称
	 */
	private String deptName;

}
