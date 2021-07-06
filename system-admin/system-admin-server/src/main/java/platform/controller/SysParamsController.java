package platform.controller;

import annotation.LogOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import page.PageData;
import platform.dto.SysParamsDTO;
import platform.excel.SysParamsExcel;
import platform.service.SysParamsService;
import utils.ExcelUtils;
import utils.Result;
import validator.AssertUtils;
import validator.ValidatorUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * 参数管理
 */
@RestController
@RequestMapping("params")
public class SysParamsController {
    @Autowired
    private SysParamsService sysParamsService;

    @GetMapping("page")
    public Result<PageData<SysParamsDTO>> page(@RequestParam Map<String, Object> params){
        PageData<SysParamsDTO> page = sysParamsService.page(params);
        return new Result<PageData<SysParamsDTO>>().ok(page);
    }

    @GetMapping("{id}")
    public Result<SysParamsDTO> get(@PathVariable("id") Long id){
        SysParamsDTO data = sysParamsService.get(id);
        return new Result<SysParamsDTO>().ok(data);
    }

    @PostMapping
    @LogOperation("Save Params")
    public Result save(@RequestBody SysParamsDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto);
        sysParamsService.save(dto);
        return new Result();
    }

    @PutMapping
    @LogOperation("Update Params")
    public Result update(@RequestBody SysParamsDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto);
        sysParamsService.update(dto);
        return new Result();
    }

    @DeleteMapping
    @LogOperation("Delete Params")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");
        sysParamsService.delete(ids);
        return new Result();
    }

    @GetMapping("export")
    @LogOperation("Export Params")
    public void export(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<SysParamsDTO> list = sysParamsService.list(params);
        ExcelUtils.exportExcelToTarget(response, null, list, SysParamsExcel.class);
    }

    /**
     * 根据参数编码，获取参数值
     * @param paramCode  参数编码
     * @return  返回参数值
     */
    @GetMapping("code/{paramCode}")
    public String getValue(@PathVariable("paramCode") String paramCode){
        return sysParamsService.getValue(paramCode);
    }

    /**
     * 根据参数编码，更新参数值
     * @param paramCode  参数编码
     * @param paramValue  参数值
     */
    @PutMapping("code/{paramCode}")
    public void updateValueByCode(@PathVariable("paramCode") String paramCode, @RequestParam("paramValue") String paramValue){
        sysParamsService.updateValueByCode(paramCode, paramValue);
    }
}