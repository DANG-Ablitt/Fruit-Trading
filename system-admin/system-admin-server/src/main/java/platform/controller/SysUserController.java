package platform.controller;

import exception.ErrorCode;
import page.PageData;
import platform.dto.PasswordDTO;
import platform.dto.SysUserDTO;
import platform.excel.SysUserExcel;
import platform.service.SysResourceService;
import platform.service.SysRoleDataScopeService;
import platform.service.SysRoleUserService;
import platform.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import security.bo.ResourceBO;
import security.password.PasswordUtils;
import security.user.SecurityStaff;
import security.user.StaffDetail;
import utils.ConvertUtils;
import utils.ExcelUtils;
import utils.Result;
import validator.AssertUtils;
import validator.ValidatorUtils;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 * @since 1.0.0
 */
@RestController
@RequestMapping("user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleUserService sysRoleUserService;
    @Autowired
    private SysRoleDataScopeService sysRoleDataScopeService;
    @Autowired
    private SysResourceService sysResourceService;

    @GetMapping("page")
    public Result<PageData<SysUserDTO>> page(@RequestParam Map<String, Object> params){
        PageData<SysUserDTO> page = sysUserService.page(params);
        return new Result<PageData<SysUserDTO>>().ok(page);
    }

    @GetMapping("{id}")
    public Result<SysUserDTO> get(@PathVariable("id") Long id){
        SysUserDTO data = sysUserService.get(id);
        //用户角色列表
        List<Long> roleIdList = sysRoleUserService.getRoleIdList(id);
        data.setRoleIdList(roleIdList);
        return new Result<SysUserDTO>().ok(data);
    }

    @GetMapping("info")
    public Result<SysUserDTO> info(StaffDetail staff){
        SysUserDTO data = ConvertUtils.sourceToTarget(staff, SysUserDTO.class);
        return new Result<SysUserDTO>().ok(data);
    }

    @PostMapping
    public Result save(@RequestBody SysUserDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto);
        sysUserService.save(dto);
        return new Result();
    }

    @PutMapping
    public Result update(@RequestBody SysUserDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto);
        sysUserService.update(dto);
        return new Result();
    }

    @PutMapping("password")
    public Result password(@RequestBody PasswordDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto);
        StaffDetail user = SecurityStaff.getStaff();
        //原密码不正确
        if(!PasswordUtils.matches(dto.getPassword(), user.getPassword())){
            return new Result().error(ErrorCode.PASSWORD_ERROR);
        }
        sysUserService.updatePassword(user.getId(), dto.getNewPassword());
        return new Result();
    }

    @DeleteMapping
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");
        sysUserService.delete(ids);
        return new Result();
    }

    @GetMapping("export")
    public void export(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<SysUserDTO> list = sysUserService.list(params);
        ExcelUtils.exportExcelToTarget(response, null, list, SysUserExcel.class);
    }

    /**
     * 根据用户名，获取用户信息
     */
    @GetMapping("getByUsername")
    public Result<StaffDetail> getByUsername(String username){
        SysUserDTO user = sysUserService.getByUsername(username);
        StaffDetail staffDetail = ConvertUtils.sourceToTarget(user, StaffDetail.class);
        //初始化用户数据
        initUserData(staffDetail);
        return new Result<StaffDetail>().ok(staffDetail);
    }

    /**
     * 根据用户ID，获取用户信息
     */
    @GetMapping("getById")
    public Result<StaffDetail> getById(Long id){
        SysUserDTO user = sysUserService.get(id);
        StaffDetail staffDetail = ConvertUtils.sourceToTarget(user, StaffDetail.class);
        //初始化用户数据
        initUserData(staffDetail);
        return new Result<StaffDetail>().ok(staffDetail);
    }

    /**
     * 初始化用户数据
     */
    private void initUserData(StaffDetail staffDetail){
        if(staffDetail == null){
            return;
        }
        //用户部门数据权限
        List<Long> deptIdList = sysRoleDataScopeService.getDataScopeList(staffDetail.getId());
        staffDetail.setDeptIdList(deptIdList);
        //用户资源列表
        List<ResourceBO> resourceList = sysResourceService.getUserResourceList(staffDetail.getId());
        staffDetail.setResourceList(resourceList);
    }

}