package platform.enums;

/**
 * 商品状态枚举
 */
public enum ProductStatusEnum {
    PUT_ON_THE_SHELF(0),
    OFF_SHELF(1);

    private int value;

    ProductStatusEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
