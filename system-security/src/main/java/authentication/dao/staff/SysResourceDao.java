package authentication.dao.staff;

import authentication.entity.SysResourceEntity;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface SysResourceDao{
    /**
     * 获取用户资源列表
     * @param userId 用户ID
     */
    List<SysResourceEntity> getUserResourceList(@Param("userId") Long userId);
}