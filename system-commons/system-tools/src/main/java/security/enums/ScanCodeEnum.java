package security.enums;

/**
 * 二维码状态枚举
 */
public enum ScanCodeEnum {
    YES(1),
    WAIT(0),
    NO(2);
    private int value;
    ScanCodeEnum(int value) {
        this.value = value;
    }
    public int value() {
        return this.value;
    }
}
