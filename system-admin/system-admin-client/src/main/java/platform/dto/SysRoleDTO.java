package platform.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色管理
 */
@Data
public class SysRoleDTO implements Serializable {
    private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private Long id;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 菜单ID列表
	 */
	private List<Long> menuIdList;
	/**
	 * 部门ID列表
	 */
	private List<Long> deptIdList;
}