package security.enums;

/**
 * 登录方式枚举
 */
public enum LoginModeEnum {
    COMMON(1),
    OTHER(2),
    COMMONPC(10),
    COMMONIPHONE(11),
    OTHERSCANCODE(20);
    private int value;
    LoginModeEnum(int value) {
        this.value = value;
    }
    public int value() {
        return this.value;
    }
}
