package platform.dao;

import mybatis_plus.dao.BaseDao;
import platform.entity.SysResourceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 资源管理
 */
@Mapper
public interface SysResourceDao extends BaseDao<SysResourceEntity> {
    /**
     * 根据资源编码，删除对应的资源
     * @param code 资源编码
     */
    void deleteByCode(String code);

    /**
     * 获取资源列表
     * @param menuId  菜单ID
     */
    List<SysResourceEntity> getMenuResourceList(String menuId);

    /**
     * 获取所有资源列表
     */
    List<SysResourceEntity> getResourceList();

    /**
     * 获取用户资源列表
     * @param userId 用户ID
     */
    List<SysResourceEntity> getUserResourceList(@Param("userId") Long userId);
}