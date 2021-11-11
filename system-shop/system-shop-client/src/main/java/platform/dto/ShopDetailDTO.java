package platform.dto;

import lombok.Data;

/**
 * 商品属性
 */
@Data
public class ShopDetailDTO {
    /**
     * 商品属性名
     */
    private String detail_name;

    /**
     * 商品属性内容
     */
    private String detail_info;
}
