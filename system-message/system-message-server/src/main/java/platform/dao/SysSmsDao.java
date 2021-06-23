package platform.dao;

import mybatis_plus.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import platform.entity.SysSmsEntity;
/**
 * 短信
 */
@Mapper
public interface SysSmsDao extends BaseDao<SysSmsEntity> {
	
}
