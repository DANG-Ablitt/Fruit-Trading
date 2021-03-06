package platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mybatis_plus.entity.BaseEntity;

/**
 * 邮件模板
 * 
 * @author Mark sunlightcs@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("sys_mail_template")
public class SysMailTemplateEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 模板名称
	 */
	private String name;
	/**
	 * 邮件主题
	 */
	private String subject;
	/**
	 * 邮件正文
	 */
	private String content;

}