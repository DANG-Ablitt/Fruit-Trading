package platform.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Spike implements Serializable {
    private static final long serialVersionUID = -5171284810277840403L;
    /**
     * 商品id
     */
    private Long shop_id;
    /**
     * 用户id
     */
    private Long user_id;
}
