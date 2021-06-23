package authentication.dao.staff;

import java.util.List;

public interface SysRoleDataScopeDao  {
    /**
     * 获取用户的部门数据权限列表
     */
    List<Long> getDataScopeList(Long userId);
}