package platform.dao;

import mybatis_plus.dao.BaseDao;
import platform.entity.ScheduleJobEntity;
import org.apache.ibatis.annotations.Mapper;
import java.util.Map;

/**
 * 定时任务
 */
@Mapper
public interface ScheduleJobDao extends BaseDao<ScheduleJobEntity> {
	
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}
