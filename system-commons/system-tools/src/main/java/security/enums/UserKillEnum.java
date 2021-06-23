package security.enums;

/**
 * 账号状态枚举
 */
public enum UserKillEnum {
    YES(1),
    NO(0);

    private int value;

    UserKillEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
