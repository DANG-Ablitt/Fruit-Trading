package platform.controller;

import platform.dto.SysDeptDTO;
import platform.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.Result;
import validator.AssertUtils;
import validator.ValidatorUtils;

import java.util.HashMap;
import java.util.List;

/**
 * 部门管理
 */
@RestController
@RequestMapping("dept")
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;

    @GetMapping("list")
    public Result<List<SysDeptDTO>> list(){
        List<SysDeptDTO> list = sysDeptService.list(new HashMap<>(1));

        return new Result<List<SysDeptDTO>>().ok(list);
    }

    @GetMapping("{id}")
    public Result<SysDeptDTO> get(@PathVariable("id") Long id){
        SysDeptDTO data = sysDeptService.get(id);
        return new Result<SysDeptDTO>().ok(data);
    }

    @PostMapping
    public Result save(@RequestBody SysDeptDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto);
        sysDeptService.save(dto);
        return new Result();
    }

    @PutMapping
    public Result update(@RequestBody SysDeptDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto);
        sysDeptService.update(dto);
        return new Result();
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") Long id){
        //效验数据
        AssertUtils.isNull(id, "id");
        sysDeptService.delete(id);
        return new Result();
    }
}