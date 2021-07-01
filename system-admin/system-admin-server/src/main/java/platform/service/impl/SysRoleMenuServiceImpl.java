package platform.service.impl;

import cn.hutool.core.collection.CollUtil;
import mybatis_plus.service.impl.BaseServiceImpl;
import platform.dao.SysRoleMenuDao;
import platform.entity.SysRoleMenuEntity;
import platform.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 角色菜单关系
 */
@Service
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenuDao, SysRoleMenuEntity> implements SysRoleMenuService {

    @Override
    public List<Long> getMenuIdList(Long roleId){
        return baseDao.getMenuIdList(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
        //先删除角色菜单关系
        deleteByRoleId(roleId);

        //角色没有一个菜单权限的情况
        if(CollUtil.isEmpty(menuIdList)){
            return ;
        }

        //保存角色菜单关系
        for(Long menuId : menuIdList){
            SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
            sysRoleMenuEntity.setMenuId(menuId);
            sysRoleMenuEntity.setRoleId(roleId);

            //保存
            insert(sysRoleMenuEntity);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByRoleId(Long roleId) {
        baseDao.deleteByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByMenuId(Long menuId) {
        baseDao.deleteByMenuId(menuId);
    }

}