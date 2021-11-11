package platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleJobDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * spring bean名称
     */
    private String beanName;
    /**
     * 参数
     */
    private String params;
    /**
     * cron表达式
     */
    private String cronExpression;
    /**
     * 任务状态
     * 0：暂停
     * 1：正常
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
}
