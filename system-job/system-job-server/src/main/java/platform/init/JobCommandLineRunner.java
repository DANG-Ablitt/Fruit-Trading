package platform.init;

import org.springframework.beans.factory.annotation.Qualifier;
import platform.dao.ScheduleJobDao;
import platform.entity.ScheduleJobEntity;
import platform.utils.ScheduleUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 初始化定时任务数据
 * SpringBoot提供了CommandLineRunner
 * 在容器启动成功后的最后进行回调，遍历所有实现了这两个接口的类加载到Spring 容器中
 * 然后执行接口实现类中的run方法
 */
@Component
public class JobCommandLineRunner implements CommandLineRunner {
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ScheduleJobDao scheduleJobDao;

    @Override
    public void run(String... args) {
        //从数据库中加载定时任务列表
        List<ScheduleJobEntity> scheduleJobList = scheduleJobDao.selectList(null);
        for(ScheduleJobEntity scheduleJob : scheduleJobList){
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getId());
            //如果不存在，则创建
            if(cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            }else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }
}