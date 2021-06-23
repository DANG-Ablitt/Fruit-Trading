package service;

import authentication.config.SpringConfig;
import authentication.config.SpringMvcConfig;
import authentication.config.WebAppInitializer;
import authentication.service.SysResourceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import security.bo.ResourceBO;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {SpringConfig.class, SpringMvcConfig.class, WebAppInitializer.class})//用于加载bean
public class SysResourceServiceTest {
    @Autowired
    private SysResourceService sysResourceService;
    /**
     * 测试从数据库获取数据
     */
    @Test
    public void test() throws Exception {
        System.out.println("123456");
        List<ResourceBO> list=sysResourceService.getUserResourceList(1067246875800000001l);
        System.out.println(list);

    }
}
