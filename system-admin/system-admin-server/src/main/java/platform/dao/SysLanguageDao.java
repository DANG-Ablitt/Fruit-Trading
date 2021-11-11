package platform.dao;

import mybatis_plus.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import platform.entity.SysLanguageEntity;

/**
 * 国际化
 */
@Mapper
public interface SysLanguageDao extends BaseDao<SysLanguageEntity> {

    SysLanguageEntity getLanguage(SysLanguageEntity entity);

    void updateLanguage(SysLanguageEntity entity);

}