package platform.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CouponReturnDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     * 产品名称
     */
    private String name;
    /**
     * 使用平台：
     * 0->全部
     * 1->移动
     * 2->PC
     */
    private Integer platform;
    /**
     * 优惠券类型:
     * 0->预购
     * 1->秒杀
     */
    private Integer type;
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
     * 状态
     * 0->未开始
     * 1->进行中
     * 2->已结束
     * 3->异常
     */
    private Integer memberlevel;
}
