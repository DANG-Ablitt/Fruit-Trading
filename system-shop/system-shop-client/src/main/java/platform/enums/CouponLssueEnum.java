package platform.enums;

/**
 * 优惠卷发放枚举
 */
public enum CouponLssueEnum {
    AUTO(0),
    MANUAL(1);

    private int value;

    CouponLssueEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
