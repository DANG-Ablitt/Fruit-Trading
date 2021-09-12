package platform.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mybatis_plus.entity.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠卷管理
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("shop_coupon")
public class CouponEntity extends BaseEntity {
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
	 * 优惠类型:
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
	private String detail;
	/**
	 * 抢购商品预约时间段
	 */
	private String time0;
	/**
	 * 抢购商品结果发布时间
	 */
	private String time1;
	/**
	 * 秒杀商品开始时间
	 */
	private String time2;
	/**
	 * 状态
	 * 0->未开始
	 * 1->进行中
	 * 2->已结束
	 * 3->异常
	 */
	private Integer memberlevel;
	/**
	 * 删除标识  0：未删除    1：删除
	 */
	@TableField(fill = FieldFill.INSERT)
	private Integer delFlag;
}