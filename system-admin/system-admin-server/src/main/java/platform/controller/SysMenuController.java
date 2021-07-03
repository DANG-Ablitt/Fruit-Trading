package platform.controller;

import constant.Constant;
import exception.ErrorCode;
import org.springframework.http.HttpHeaders;
import platform.dto.MenuResourceDTO;
import platform.dto.SysMenuDTO;
import platform.service.SysMenuService;
import platform.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.StaffDetailRedis;
import security.user.StaffDetail;
import security.user.UserDetail;
import utils.HttpContextUtils;
import utils.Result;
import validator.AssertUtils;
import validator.ValidatorUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * 菜单管理
 */
@RestController
@RequestMapping("menu")
public class SysMenuController {
    @Autowired
    private StaffDetailRedis staffDetailRedis;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysResourceService sysResourceService;

    @GetMapping("nav")
    public Result<List<SysMenuDTO>> nav(StaffDetail staffDetail1){
        //从请求头中获取用户id
        HttpServletRequest request=new HttpContextUtils().getHttpServletRequest();
        String key=request.getHeader(Constant.USER_KEY);
        //从缓存中获取消息
        StaffDetail staffDetail=staffDetailRedis.get(Long.valueOf(key));
        List<SysMenuDTO> list = sysMenuService.getUserMenuNavList(staffDetail);
        return new Result<List<SysMenuDTO>>().ok(list);
    }

    @GetMapping("permissions")
    public Result<Set<String>> permissions(StaffDetail staffDetail){
        Set<String> set = sysMenuService.getUserPermissions(staffDetail);
        return new Result<Set<String>>().ok(set);
    }

    @GetMapping("list")
    public Result<List<SysMenuDTO>> list(Integer type){
        List<SysMenuDTO> list = sysMenuService.getMenuList(type);
        return new Result<List<SysMenuDTO>>().ok(list);
    }

    @GetMapping("{id}")
    public Result<SysMenuDTO> get(@PathVariable("id") Long id){
        SysMenuDTO data = sysMenuService.get(id);
        //菜单资源列表
        List<MenuResourceDTO> resourceList = sysResourceService.getMenuResourceList(id);
        data.setResourceList(resourceList);
        return new Result<SysMenuDTO>().ok(data);
    }

    @PostMapping
    public Result save(@RequestBody SysMenuDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto);
        sysMenuService.save(dto);
        return new Result();
    }

    @PutMapping
    public Result update(@RequestBody SysMenuDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto);
        sysMenuService.update(dto);
        return new Result();
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") Long id){
        //效验数据
        AssertUtils.isNull(id, "id");
        //判断是否有子菜单或按钮
        List<SysMenuDTO> list = sysMenuService.getListPid(id);
        if(list.size() > 0){
            return new Result().error(ErrorCode.SUB_MENU_EXIST);
        }
        sysMenuService.delete(id);
        return new Result();
    }

    @GetMapping("select")
    public Result<List<SysMenuDTO>> select(StaffDetail staffDetail){
        List<SysMenuDTO> list = sysMenuService.getUserMenuList(staffDetail, null);
        return new Result<List<SysMenuDTO>>().ok(list);
    }

}