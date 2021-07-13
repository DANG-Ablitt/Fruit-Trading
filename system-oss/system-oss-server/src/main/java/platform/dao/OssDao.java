package platform.dao;

import mybatis_plus.dao.BaseDao;
import platform.entity.OssEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件上传
 */
@Mapper
public interface OssDao extends BaseDao<OssEntity> {
	
}
