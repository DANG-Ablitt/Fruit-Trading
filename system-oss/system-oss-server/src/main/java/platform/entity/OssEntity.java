package platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mybatis_plus.entity.BaseEntity;

/**
 * 文件上传
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("sys_oss")
public class OssEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * URL地址
	 */
	private String url;

}