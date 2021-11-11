package platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import constant.Constant;
import mybatis_plus.service.impl.BaseServiceImpl;
import page.PageData;
import platform.dao.ScheduleJobDao;
import platform.dto.ScheduleJobDTO;
import platform.entity.ScheduleJobEntity;
import platform.enums.ScheduleStatusEnum;
import platform.service.ScheduleJobService;
import platform.utils.ScheduleUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.ConvertUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class ScheduleJobServiceImpl extends BaseServiceImpl<ScheduleJobDao, ScheduleJobEntity> implements ScheduleJobService {
	@Autowired
	private Scheduler scheduler;

	@Override
	public PageData<ScheduleJobDTO> page(Map<String, Object> params) {
		IPage<ScheduleJobEntity> page = baseDao.selectPage(
			getPage(params, Constant.CREATE_DATE, false),
			getWrapper(params)
		);
		return getPageData(page, ScheduleJobDTO.class);
	}

	@Override
	public ScheduleJobDTO get(Long id) {
		ScheduleJobEntity entity = baseDao.selectById(id);

		return ConvertUtils.sourceToTarget(entity, ScheduleJobDTO.class);
	}

	private QueryWrapper<ScheduleJobEntity> getWrapper(Map<String, Object> params){
		String beanName = (String)params.get("beanName");

		QueryWrapper<ScheduleJobEntity> wrapper = new QueryWrapper<>();
		wrapper.like(StringUtils.isNotBlank(beanName), "bean_name", beanName);

		return wrapper;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(ScheduleJobDTO dto) {
		ScheduleJobEntity entity = ConvertUtils.sourceToTarget(dto, ScheduleJobEntity.class);
		entity.setStatus(ScheduleStatusEnum.NORMAL.value());
        this.insert(entity);
        ScheduleUtils.createScheduleJob(scheduler, entity);
    }
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(ScheduleJobDTO dto) {
		ScheduleJobEntity entity = ConvertUtils.sourceToTarget(dto, ScheduleJobEntity.class);

        ScheduleUtils.updateScheduleJob(scheduler, entity);
                
        this.updateById(entity);
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] ids) {
    	for(Long id : ids){
    		ScheduleUtils.deleteScheduleJob(scheduler, id);
    	}
    	
    	//删除数据
    	this.deleteBatchIds(Arrays.asList(ids));
	}

	@Override
    public int updateBatch(Long[] ids, int status){
    	Map<String, Object> map = new HashMap<>(2);
    	map.put("ids", ids);
    	map.put("status", status);
    	return baseDao.updateBatch(map);
    }
    
	@Override
	@Transactional(rollbackFor = Exception.class)
    public void run(Long[] ids) {
    	for(Long id : ids){
    		ScheduleUtils.run(scheduler, this.selectById(id));
    		System.out.println(scheduler);
    	}
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
    public void pause(Long[] ids) {
        for(Long id : ids){
    		ScheduleUtils.pauseJob(scheduler, id);
    	}
        
    	updateBatch(ids, ScheduleStatusEnum.PAUSE.value());
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
    public void resume(Long[] ids) {
    	for(Long id : ids){
    		ScheduleUtils.resumeJob(scheduler, id);
    	}

    	updateBatch(ids, ScheduleStatusEnum.NORMAL.value());
    }
    
}