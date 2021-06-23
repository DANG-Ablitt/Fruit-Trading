package platform.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务日志
 */
@Data
public class ScheduleJobLogDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 任务id
     */
    private Long jobId;
    /**
     * spring bean名称
     */
    private String beanName;
    /**
     * 参数
     */
    private String params;
    /**
     * 任务状态
     * 0：失败
     * 1：成功
     */
    private Integer status;
    /**
     * 失败信息
     */
    private String error;
    /**
     * 耗时(单位：毫秒)
     */
    private Integer times;
    /**
     * 创建时间
     */
    private Date createDate;
}
