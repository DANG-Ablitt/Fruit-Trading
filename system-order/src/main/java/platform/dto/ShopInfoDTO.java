package platform.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShopInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    private Long shop_id;
    /**
     * 用户id
     */
    private Long user_id;
}
