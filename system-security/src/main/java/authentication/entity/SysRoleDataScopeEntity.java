package authentication.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色数据权限
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SysRoleDataScopeEntity  {
	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	private Long roleId;
	/**
	 * 部门ID
	 */
	private Long deptId;

}