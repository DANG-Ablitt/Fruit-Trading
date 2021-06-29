package service;

import authentication.config.SpringConfig;
import authentication.config.SpringMvcConfig;
import authentication.config.WebAppInitializer;
import authentication.dao.staff.SysStaffDao;
import authentication.entity.SysStaffEntity;
import authentication.service.CaptchaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utils.SpringContextUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {SpringConfig.class, SpringMvcConfig.class, WebAppInitializer.class})//用于加载bean
public class SysStaffServiceTest {
    @Autowired
    private SysStaffDao sysStaffDao;
    /**
     * 测试从数据库获取数据
     */
    @Test
    public void test() throws Exception {
        SysStaffEntity list=sysStaffDao.getByUsername("admin");
        System.out.println(list);
        //System.out.println(SpringContextUtils.getBean(CaptchaService.class));

    }
}
