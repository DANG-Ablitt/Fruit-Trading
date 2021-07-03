package platform.controller;

import page.PageData;
import platform.dto.SysLogLoginDTO;
import platform.excel.SysLogLoginExcel;
import platform.service.SysLogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.ExcelUtils;
import utils.Result;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 登录日志
 */
@RestController
@RequestMapping("log/login")
public class SysLogLoginController {
    @Autowired
    private SysLogLoginService sysLogLoginService;

    @GetMapping("page")
    public Result<PageData<SysLogLoginDTO>> page(@RequestParam Map<String, Object> params){
        PageData<SysLogLoginDTO> page = sysLogLoginService.page(params);

        return new Result<PageData<SysLogLoginDTO>>().ok(page);
    }

    @GetMapping("export")
    public void export(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<SysLogLoginDTO> list = sysLogLoginService.list(params);
        ExcelUtils.exportExcelToTarget(response, null, list, SysLogLoginExcel.class);
    }

}