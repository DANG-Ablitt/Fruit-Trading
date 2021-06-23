package platform.controller;

import page.PageData;
import platform.dto.ScheduleJobLogDTO;
import platform.service.ScheduleJobLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
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
	public Result<PageData<ScheduleJobLogDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
		PageData<ScheduleJobLogDTO> page = scheduleJobLogService.page(params);
		
		return new Result<PageData<ScheduleJobLogDTO>>().ok(page);
	}

	@GetMapping("{id}")
	public Result<ScheduleJobLogDTO> info(@PathVariable("id") Long id){
		ScheduleJobLogDTO log = scheduleJobLogService.get(id);
		return new Result<ScheduleJobLogDTO>().ok(log);
	}
}