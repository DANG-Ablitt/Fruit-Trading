package platform.controller;

 import page.PageData;
 import platform.dto.SysRoleDTO;
 import platform.service.SysRoleDataScopeService;
 import platform.service.SysRoleMenuService;
 import platform.service.SysRoleService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.*;
 import utils.Result;
 import validator.AssertUtils;
 import validator.ValidatorUtils;

 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;

 /**
 * 角色管理
 */
@RestController
@RequestMapping("role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysRoleDataScopeService sysRoleDataScopeService;

    @GetMapping("page")
    public Result<PageData<SysRoleDTO>> page(@RequestParam Map<String, Object> params){
        PageData<SysRoleDTO> page = sysRoleService.page(params);
        return new Result<PageData<SysRoleDTO>>().ok(page);
    }

    @GetMapping("list")
    public Result<List<SysRoleDTO>> list(){
        List<SysRoleDTO> data = sysRoleService.list(new HashMap<>(1));
        return new Result<List<SysRoleDTO>>().ok(data);
    }

    @GetMapping("{id}")
    public Result<SysRoleDTO> get(@PathVariable("id") Long id){
        SysRoleDTO data = sysRoleService.get(id);
        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.getMenuIdList(id);
        data.setMenuIdList(menuIdList);
        //查询角色对应的数据权限
        List<Long> deptIdList = sysRoleDataScopeService.getDeptIdList(id);
        data.setDeptIdList(deptIdList);
        return new Result<SysRoleDTO>().ok(data);
    }

    @PostMapping
    public Result save(@RequestBody SysRoleDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto);
        sysRoleService.save(dto);
        return new Result();
    }

    @PutMapping
    public Result update(@RequestBody SysRoleDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto);
        sysRoleService.update(dto);
        return new Result();
    }

    @DeleteMapping
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");
        sysRoleService.delete(ids);
        return new Result();
    }

}