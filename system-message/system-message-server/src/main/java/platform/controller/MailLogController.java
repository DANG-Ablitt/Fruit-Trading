package platform.controller;

import page.PageData;
import platform.dto.SysMailLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import platform.service.SysMailLogService;
import utils.Result;
import java.util.Arrays;
import java.util.Map;


/**
 * 邮件发送记录
 */
@RestController
@RequestMapping("maillog")
public class MailLogController {
    @Autowired
    private SysMailLogService sysMailLogService;

    @GetMapping("page")
    public Result<PageData<SysMailLogDTO>> page(@RequestParam Map<String, Object> params){
        PageData<SysMailLogDTO> page = sysMailLogService.page(params);
        return new Result<PageData<SysMailLogDTO>>().ok(page);
    }

    @DeleteMapping
    public Result delete(@RequestBody Long[] ids){
        sysMailLogService.deleteBatchIds(Arrays.asList(ids));
        return new Result();
    }

}