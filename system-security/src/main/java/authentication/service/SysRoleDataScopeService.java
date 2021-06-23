package authentication.service;

import authentication.dao.staff.SysRoleDataScopeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 角色数据权限
 */
@Service
public class SysRoleDataScopeService {
    @Autowired
    private SysRoleDataScopeDao sysRoleDataScopeDao;

    public List<Long> getDataScopeList(Long userId)
    {
        return sysRoleDataScopeDao.getDataScopeList(userId);
    }

}