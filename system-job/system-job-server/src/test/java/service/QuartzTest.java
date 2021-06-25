package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import platform.JobApplication;
import platform.service.ScheduleJobService;
import utils.SpringContextUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=JobApplication.class)
public class QuartzTest {

    @Autowired
    private ScheduleJobService scheduleJobService;
    @Test
    public void test(){
        //立即执行
        //scheduleJobService.run(new Long[]{1067246875800000144L});
        System.out.println(SpringContextUtils.getBean("testTask"));
    }
}
