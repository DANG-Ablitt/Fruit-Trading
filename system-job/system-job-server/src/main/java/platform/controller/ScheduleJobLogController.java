package platform.controller;

import page.PageData;
import platform.dto.ScheduleJobLogDTO;
import platform.service.ScheduleJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.Result;
import java.util.Map;

/**
 * 定时任务日志
 */
@RestController
@RequestMapping("scheduleLog")
public class ScheduleJobLogController {
	@Autowired
	private ScheduleJobLogService scheduleJobLogService;

	@GetMapping("page")
	public Result<PageData<ScheduleJobLogDTO>> page(@RequestParam Map<String, Object> params){
		PageData<ScheduleJobLogDTO> page = scheduleJobLogService.page(params);
		
		return new Result<PageData<ScheduleJobLogDTO>>().ok(page);
	}

	@GetMapping("{id}")
	public Result<ScheduleJobLogDTO> info(@PathVariable("id") Long id){
		ScheduleJobLogDTO log = scheduleJobLogService.get(id);
		return new Result<ScheduleJobLogDTO>().ok(log);
	}
}