package platform.enums;

public enum CouponPlatformEnum {

    ALL(0),
    MOBILE(1),
    PC(2);

    private int value;

    CouponPlatformEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
