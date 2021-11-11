package platform.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 优惠商品
 */
@Data
public class PmsCouponDTO implements Serializable {
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
     * 产品品牌
     */
    private String pic;
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
     * 图片url
     */
    private String url;
    /**
     * 详细参数
     */
    private List<ShopDetailDTO> detailList;
    /**
     * 抢购商品时间
     */
    //抢购时间段
    private List<String> time0 = null;
    //抢购商品结果发布时间
    private String time1;
    /**
     * 秒杀商品开始时间
     */
    private String time2;
}
