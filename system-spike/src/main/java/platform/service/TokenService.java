package platform.service;

import mybatis_plus.service.BaseService;
import platform.entity.TokenEntity;

/**
 * 根据用户Token查询
 */
public interface TokenService extends BaseService<TokenEntity> {

	TokenEntity getByToken(String token);

}
