package platform.service.impl;

import constant.Constant;
import enums.SuperAdminEnum;
import exception.ErrorCode;
import exception.RenException;
import mybatis_plus.service.impl.BaseServiceImpl;
import platform.dao.SysMenuDao;
import platform.dto.SysMenuDTO;
import platform.entity.SysMenuEntity;
import platform.enums.MenuTypeEnum;
import platform.redis.SysMenuRedis;
import platform.service.SysLanguageService;
import platform.service.SysMenuService;
import platform.service.SysResourceService;
import platform.service.SysRoleMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import security.user.SecurityStaff;
import security.user.StaffDetail;
import utils.ConvertUtils;
import utils.HttpContextUtils;
import utils.TreeUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 菜单管理
 */
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {
    @Autowired
    private SysMenuRedis sysMenuRedis;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysResourceService sysResourceService;
    @Autowired
    private SysLanguageService sysLanguageService;

    @Override
    public SysMenuDTO get(Long id) {
        SysMenuEntity entity = baseDao.getById(id, HttpContextUtils.getLanguage());

        SysMenuDTO dto = ConvertUtils.sourceToTarget(entity, SysMenuDTO.class);

        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysMenuDTO dto) {
        SysMenuEntity entity = ConvertUtils.sourceToTarget(dto, SysMenuEntity.class);

        //保存菜单
        insert(entity);
        saveLanguage(entity.getId(), "name", entity.getName());

        //保存菜单资源
        sysResourceService.saveMenuResource(entity.getId(), entity.getName(), dto.getResourceList());

        //清空当前用户，菜单导航、权限标识
        sysMenuRedis.delete(SecurityStaff.getStaffId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysMenuDTO dto) {
        SysMenuEntity entity = ConvertUtils.sourceToTarget(dto, SysMenuEntity.class);

        //上级菜单不能为自身
        if(entity.getId().equals(entity.getPid())){
            throw new RenException(ErrorCode.SUPERIOR_MENU_ERROR);
        }

        //更新菜单
        updateById(entity);
        saveLanguage(entity.getId(), "name", entity.getName());

        //更新菜单资源
        sysResourceService.saveMenuResource(entity.getId(), entity.getName(), dto.getResourceList());

        //清空当前用户，菜单导航、权限标识
        sysMenuRedis.delete(SecurityStaff.getStaffId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        //逻辑删除
        logicDelete(new Long[]{id}, SysMenuEntity.class);

        //删除角色菜单关系
        sysRoleMenuService.deleteByMenuId(id);

        //清空当前用户，菜单导航、权限标识
        sysMenuRedis.delete(SecurityStaff.getStaffId());
    }

    @Override
    public List<SysMenuDTO> getMenuList(Integer type) {
        List<SysMenuEntity> menuList = baseDao.getMenuList(type, HttpContextUtils.getLanguage());

        List<SysMenuDTO> dtoList = ConvertUtils.sourceToTarget(menuList, SysMenuDTO.class);

        return TreeUtils.build(dtoList, Constant.MENU_ROOT);
    }

    @Override
    public List<SysMenuDTO> getUserMenuList(StaffDetail staffDetail, Integer type) {
        List<SysMenuEntity> menuList;
        //系统管理员，拥有最高权限
        if(staffDetail.getSuperAdmin() == SuperAdminEnum.YES.value()){
            menuList = baseDao.getMenuList(type, HttpContextUtils.getLanguage());
        }else {
            menuList = baseDao.getUserMenuList(staffDetail.getId(), type, HttpContextUtils.getLanguage());
        }

        List<SysMenuDTO> dtoList = ConvertUtils.sourceToTarget(menuList, SysMenuDTO.class);

        return TreeUtils.build(dtoList);
    }

    @Override
    public List<SysMenuDTO> getUserMenuNavList(StaffDetail staffDetail) {
        List<SysMenuDTO> menuList = sysMenuRedis.getUserMenuNavList(staffDetail.getId());
        if(menuList == null){
            menuList = getUserMenuList(staffDetail, MenuTypeEnum.MENU.value());
            sysMenuRedis.setUserMenuNavList(staffDetail.getId(), menuList);
        }

        return menuList;
    }

    @Override
    public Set<String> getUserPermissions(StaffDetail staffDetail) {
        //用户权限列表
        Set<String> permsSet = sysMenuRedis.getUserPermissions(staffDetail.getId());
        if(permsSet != null){
            return permsSet;
        }

        //超级管理员，拥有最高权限
        List<SysMenuEntity> menuList;
        if(staffDetail.getSuperAdmin() == SuperAdminEnum.YES.value()){
            menuList = baseDao.getMenuList(MenuTypeEnum.BUTTON.value(), HttpContextUtils.getLanguage());
        }else{
            menuList = baseDao.getUserMenuList(staffDetail.getId(), MenuTypeEnum.BUTTON.value(), HttpContextUtils.getLanguage());
        }

        permsSet = new HashSet<>();
        for(SysMenuEntity menu : menuList){
            if(StringUtils.isNotBlank(menu.getPermissions())){
                permsSet.add(menu.getPermissions());
            }
        }

        //保存到缓存
        sysMenuRedis.setUserPermissions(staffDetail.getId(), permsSet);

        return permsSet;
    }

    @Override
    public List<SysMenuDTO> getListPid(Long pid) {
        List<SysMenuEntity> menuList = baseDao.getListPid(pid);

        return ConvertUtils.sourceToTarget(menuList, SysMenuDTO.class);
    }

    private void saveLanguage(Long tableId, String fieldName, String fieldValue){
        sysLanguageService.saveOrUpdate("sys_menu", tableId, fieldName, fieldValue, HttpContextUtils.getLanguage());
    }

}