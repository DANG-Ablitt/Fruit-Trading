
package platform.service.impl;

import cn.hutool.core.collection.CollUtil;
import mybatis_plus.service.impl.BaseServiceImpl;
import platform.dao.SysResourceDao;
import platform.dto.MenuResourceDTO;
import platform.entity.SysResourceEntity;
import platform.enums.MenuFlagEnum;
import platform.redis.SysResourceRedis;
import platform.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import security.bo.ResourceBO;
import utils.ConvertUtils;

import java.util.List;

/**
 * 资源管理
 */
@Service
public class SysResourceServiceImpl extends BaseServiceImpl<SysResourceDao, SysResourceEntity> implements SysResourceService {
    @Autowired
    private SysResourceRedis sysResourceRedis;

    @Override
    public List<MenuResourceDTO> getMenuResourceList(Long menuId) {
        List<SysResourceEntity> entityList = baseDao.getMenuResourceList(menuId+"");
        return ConvertUtils.sourceToTarget(entityList, MenuResourceDTO.class);
    }

    /**
     * 获取资源列表
     * 网关需要获取所有资源来判断请求是否有权限访问
     */
    @Override
    public List<ResourceBO> getResourceList() {
        List<SysResourceEntity> entityList = sysResourceRedis.get();
        if(entityList == null){
            entityList = baseDao.getResourceList();
            sysResourceRedis.set(entityList);
        }
        return ConvertUtils.sourceToTarget(entityList, ResourceBO.class);
    }

    @Override
    public List<ResourceBO> getUserResourceList(Long userId) {
        List<SysResourceEntity> entityList = baseDao.getUserResourceList(userId);
        return ConvertUtils.sourceToTarget(entityList, ResourceBO.class);
    }

    @Override
    public void saveMenuResource(Long menuId, String menuName, List<MenuResourceDTO> resourceList) {
        //先删除菜单资源关系
        baseDao.deleteByCode(menuId+"");
        //删除缓存
        sysResourceRedis.delete();
        //菜单没有一个资源的情况
        if(CollUtil.isEmpty(resourceList)){
            return ;
        }
        //保存菜单资源关系
        for(MenuResourceDTO dto : resourceList){
            SysResourceEntity entity = new SysResourceEntity();
            entity.setResourceCode(menuId+"");
            entity.setResourceName(menuName);
            entity.setResourceUrl(dto.getResourceUrl());
            entity.setResourceMethod(dto.getResourceMethod());
            //entity.setAuthLevel(ResourceAuthEnum.PERMISSIONS_AUTH.value());
            entity.setMenuFlag(MenuFlagEnum.YES.value());
            //保存
            insert(entity);
        }
    }
}