package platform.controller;

import page.PageData;
import platform.dto.SysLogErrorDTO;
import platform.excel.SysLogErrorExcel;
import platform.service.SysLogErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.ExcelUtils;
import utils.Result;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 异常日志
 */
@RestController
@RequestMapping("log/error")
public class SysLogErrorController {
    @Autowired
    private SysLogErrorService sysLogErrorService;

    @GetMapping("page")
    public Result<PageData<SysLogErrorDTO>> page(@RequestParam Map<String, Object> params){
        PageData<SysLogErrorDTO> page = sysLogErrorService.page(params);
        return new Result<PageData<SysLogErrorDTO>>().ok(page);
    }

    @GetMapping("export")
    public void export(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<SysLogErrorDTO> list = sysLogErrorService.list(params);
        ExcelUtils.exportExcelToTarget(response, null, list, SysLogErrorExcel.class);
    }

}