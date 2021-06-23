package platform.enums;

/**
 * 定时任务状态
 */
public enum ScheduleStatusEnum {
    /**
     * 暂停
     */
    PAUSE(0),
    /**
     * 正常
     */
    NORMAL(1);

    private int value;

    ScheduleStatusEnum(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
