package platform.service;

import mybatis_plus.service.BaseService;
import platform.dto.MenuResourceDTO;
import platform.entity.SysResourceEntity;
import security.bo.ResourceBO;

import java.util.List;

/**
 * 资源管理
 */
public interface SysResourceService extends BaseService<SysResourceEntity> {

    /**
     * 获取菜单资源列表
     * @param menuId  菜单ID
     */
    List<MenuResourceDTO> getMenuResourceList(Long menuId);

    /**
     * 获取所有资源列表
     */
    List<ResourceBO> getResourceList();

    /**
     * 获取用户资源列表
     * @param userId   用户ID
     */
    List<ResourceBO> getUserResourceList(Long userId);

    /**
     * 保存菜单资源
     * @param menuId         菜单ID
     * @param menuName       菜单名称
     * @param resourceList   资源列表
     */
    void saveMenuResource(Long menuId, String menuName, List<MenuResourceDTO> resourceList);
}