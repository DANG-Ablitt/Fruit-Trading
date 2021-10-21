package platform.service.impl;

import mybatis_plus.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import platform.dao.TokenDao;
import platform.entity.TokenEntity;
import platform.service.TokenService;

@Service
public class TokenServiceImpl extends BaseServiceImpl<TokenDao, TokenEntity> implements TokenService {

	@Override
	public TokenEntity getByToken(String token) {
		return baseDao.getByToken(token);
	}

}
