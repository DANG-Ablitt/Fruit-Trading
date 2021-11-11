package platform.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单信息
 */
@Data
@TableName("b_order")
public class OrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 订单id
     */
    @TableId
    private Long Id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 商品id
     */
    private Long shopId;
    /**
     * 订单状态
     * 1：订单待支付
     * 2：订单待取货
     * 3：订单已完成
     * 4：订单已过期
     * 5：订单已取消
     */
    private Integer orderStatus;
    /**
     * 取货门店
     */
    private String address;
    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;
    /**
     * 商品数量
     */
    private Integer goodsAmount;
    /**
     * 取货码
     */
    private String shopma;
    /**
     * 生成时间
     */
    private Date nowDate;
    /**
     * 修改时间
     */
    private Date updateDate;

}
