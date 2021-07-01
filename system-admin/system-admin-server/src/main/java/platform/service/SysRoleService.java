package platform.service;

import mybatis_plus.service.BaseService;
import page.PageData;
import platform.dto.SysRoleDTO;
import platform.entity.SysRoleEntity;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 */
public interface SysRoleService extends BaseService<SysRoleEntity> {

    PageData<SysRoleDTO> page(Map<String, Object> params);

    List<SysRoleDTO> list(Map<String, Object> params);

    SysRoleDTO get(Long id);

    void save(SysRoleDTO dto);

    void update(SysRoleDTO dto);

    void delete(Long[] ids);

}