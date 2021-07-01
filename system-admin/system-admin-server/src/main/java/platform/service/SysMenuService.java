package platform.service;

import mybatis_plus.service.BaseService;
import platform.dto.SysMenuDTO;
import platform.entity.SysMenuEntity;
import security.user.StaffDetail;

import java.util.List;
import java.util.Set;

/**
 * 菜单管理
 */
public interface SysMenuService extends BaseService<SysMenuEntity> {

    SysMenuDTO get(Long id);

    void save(SysMenuDTO dto);

    void update(SysMenuDTO dto);

    void delete(Long id);

    /**
     * 菜单列表
     *
     * @param type 菜单类型
     */
    List<SysMenuDTO> getMenuList(Integer type);

    /**
     * 用户菜单列表
     *
     * @param staffDetail 用户信息
     * @param type 菜单类型
     */
    List<SysMenuDTO> getUserMenuList(StaffDetail staffDetail, Integer type);

    /**
     * 用户菜单导航
     * @param staffDetail 用户信息
     */
    List<SysMenuDTO> getUserMenuNavList(StaffDetail staffDetail);

    /**
     * 获取用户权限标识
     */
    Set<String> getUserPermissions(StaffDetail staffDetail);

    /**
     * 根据父菜单，查询子菜单
     * @param pid  父菜单ID
     */
    List<SysMenuDTO> getListPid(Long pid);
}