package platform.enums;

/**
 * 平台枚举
 */
public enum PlatformEnum {
    /**
     * 阿里云
     */
    ALIYUN(1),
    /**
     * 腾讯云
     */
    QCLOUD(2),
    /**
     * 短信宝
     */
    DUANXINBAO(3);

    private int value;

    PlatformEnum(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}