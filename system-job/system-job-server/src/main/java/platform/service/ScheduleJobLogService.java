package platform.service;

import mybatis_plus.service.BaseService;
import page.PageData;
import platform.dto.ScheduleJobLogDTO;
import platform.entity.ScheduleJobLogEntity;
import java.util.Map;

/**
 * 定时任务日志
 */
public interface ScheduleJobLogService extends BaseService<ScheduleJobLogEntity> {

	PageData<ScheduleJobLogDTO> page(Map<String, Object> params);

	ScheduleJobLogDTO get(Long id);
}
