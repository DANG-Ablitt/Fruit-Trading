package platform.controller;

import platform.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import security.bo.ResourceBO;

import java.util.List;

/**
 * 资源管理
 */
@RestController
@RequestMapping("resource")
public class SysResourceController {
    @Autowired
    private SysResourceService sysResourceService;

    /**
     * 获取所有资源列表
     */
    @GetMapping("list")
    public List<ResourceBO> list(){
        return sysResourceService.getResourceList();
    }
}