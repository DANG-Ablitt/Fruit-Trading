package platform.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShopListDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 商品id
     */
    private Long id;
    /**
     * 所在部门
     */
    private String dept;
    /**
     * 产品名称
     */
    private String name;
    /**
     * 数量
     */
    private Integer count;
    /**
     * 优惠价
     */
    private String amount1;
    /**
     * 原价
     */
    private String amount2;
    /**
     * 图片url
     */
    private String url;
    /**
     * 秒杀商品开始时间
     */
    private String time2;
}
