package platform.controller;

import page.PageData;
import platform.dto.SysLogOperationDTO;
import platform.excel.SysLogOperationExcel;
import platform.service.SysLogOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.ExcelUtils;
import utils.Result;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * 操作日志
 */
@RestController
@RequestMapping("log/operation")
public class SysLogOperationController {
    @Autowired
    private SysLogOperationService sysLogOperationService;

    @GetMapping("page")
    public Result<PageData<SysLogOperationDTO>> page(@RequestParam Map<String, Object> params){
        PageData<SysLogOperationDTO> page = sysLogOperationService.page(params);

        return new Result<PageData<SysLogOperationDTO>>().ok(page);
    }

    @GetMapping("export")
    public void export(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<SysLogOperationDTO> list = sysLogOperationService.list(params);
        ExcelUtils.exportExcelToTarget(response, null, list, SysLogOperationExcel.class);
    }

}