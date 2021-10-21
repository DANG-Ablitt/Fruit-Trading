package platform.dao;

import mybatis_plus.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import platform.entity.TokenEntity;

/**
 * 用户Token
 */
@Mapper
public interface TokenDao extends BaseDao<TokenEntity> {

    TokenEntity getByToken(String token);
}
