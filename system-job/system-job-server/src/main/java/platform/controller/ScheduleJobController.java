package platform.controller;

import page.PageData;
import platform.dto.ScheduleJobDTO;
import platform.service.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.Result;
import validator.ValidatorUtils;
import java.util.Map;

/**
 * 定时任务
 */
@RestController
@RequestMapping("schedule")
public class ScheduleJobController {
	@Autowired
	private ScheduleJobService scheduleJobService;

	@GetMapping("page")
	public Result<PageData<ScheduleJobDTO>> page(@RequestParam Map<String, Object> params){
		PageData<ScheduleJobDTO> page = scheduleJobService.page(params);
		return new Result<PageData<ScheduleJobDTO>>().ok(page);
	}

	@GetMapping("{id}")
	public Result<ScheduleJobDTO> info(@PathVariable("id") Long id){
		ScheduleJobDTO schedule = scheduleJobService.get(id);
		return new Result<ScheduleJobDTO>().ok(schedule);
	}

	@PostMapping
	public Result save(@RequestBody ScheduleJobDTO dto){
		ValidatorUtils.validateEntity(dto);
		scheduleJobService.save(dto);
		return new Result();
	}

	@PutMapping
	public Result update(@RequestBody ScheduleJobDTO dto){
		ValidatorUtils.validateEntity(dto);
		scheduleJobService.update(dto);
		return new Result();
	}

	@DeleteMapping
	public Result delete(@RequestBody Long[] ids){
		scheduleJobService.deleteBatch(ids);
		return new Result();
	}

	@PutMapping("/run")
	public Result run(@RequestBody Long[] ids){
		scheduleJobService.run(ids);
		return new Result();
	}

	@PutMapping("/pause")
	public Result pause(@RequestBody Long[] ids){
		scheduleJobService.pause(ids);
		return new Result();
	}

	@PutMapping("/resume")
	public Result resume(@RequestBody Long[] ids){
		scheduleJobService.resume(ids);
		return new Result();
	}

}