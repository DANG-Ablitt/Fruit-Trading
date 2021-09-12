package platform.enums;

/**
 * 优惠卷类型枚举
 */
public enum CouponTypeEnum {
    ALL(0),
    PRE_ORDER(1),
    SHOPPING(2),
    REGISTER(3);

    private int value;

    CouponTypeEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
