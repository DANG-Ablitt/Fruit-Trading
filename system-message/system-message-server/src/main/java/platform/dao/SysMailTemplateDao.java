package platform.dao;


import mybatis_plus.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import platform.entity.SysMailTemplateEntity;


/**
 * 邮件模板
 */
@Mapper
public interface SysMailTemplateDao extends BaseDao<SysMailTemplateEntity> {
	
}
