package platform.enums;

/**
 * OSS类型枚举
 */
public enum OssTypeEnum {
    /**
     * Minio云盘
     */
    MINIO(1);

    private int value;

    OssTypeEnum(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}