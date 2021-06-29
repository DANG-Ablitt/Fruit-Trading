package platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import utils.SpringContextUtils;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * 定时任务配置
 */
@Configuration
public class ScheduleConfig {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        //引入mqsql数据源
        factory.setDataSource(dataSource);
        /**
         * quartz参数（这里不使用quartz.properties配置文件）
         */
        Properties prop = new Properties();
        //区分特定的调度器实例，可以按照功能用途给调度器起名
        prop.put("org.quartz.scheduler.instanceName", "Scheduler");
        //必须唯一，在集群环境中作为集群的唯一key（这里配置quartz自动生成）
        prop.put("org.quartz.scheduler.instanceId", "AUTO");
        /**
         * 线程池配置
         */
        //配置线程池实现类
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        //处理job的线程个数（至少为1，最多不要超过100）
        prop.put("org.quartz.threadPool.threadCount", "20");
        //线程的优先级（1~10）
        prop.put("org.quartz.threadPool.threadPriority", "5");
        factory.setQuartzProperties(prop);
        factory.setSchedulerName("Scheduler");
        //延时启动
        factory.setStartupDelay(30);
        factory.setApplicationContextSchedulerContextKey("applicationContextKey");
        //可选，QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        factory.setOverwriteExistingJobs(true);
        //设置自动启动，默认为true
        factory.setAutoStartup(true);
        return factory;
    }

    /**
     *注意：必须在这里配置
     * 否则spring无法自动调用setApplicationContext
     * 使用时会出现无法获取applicationContext，并抛出NullPointerException
     */
    @Bean
    public SpringContextUtils springContextsUtil(){
        return new SpringContextUtils();
    }
}
