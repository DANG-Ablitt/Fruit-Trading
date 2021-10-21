package platform.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * 秒杀商品
 */
@Data
public class SpikeDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 商品id
     */
    private Long id;
    /**
     * 用户token
     */
    private String token;
    /**
     * 签名
     */
    private String sign;
}
