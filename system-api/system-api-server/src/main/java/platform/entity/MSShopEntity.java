package platform.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mybatis_plus.entity.BaseEntity;

import java.io.Serializable;

/**
 * 优惠卷管理
 */
@Data
public class MSShopEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品id
	 */
	private Long id;
	/**
	 * 所在部门id
	 */
	private Long deptId;
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