package platform.dao;

import mybatis_plus.dao.BaseDao;
import platform.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色管理
 */
@Mapper
public interface SysRoleDao extends BaseDao<SysRoleEntity> {
	
}
