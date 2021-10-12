package platform.service.impl;

import exception.ErrorCode;
import exception.RenException;
import mybatis_plus.service.impl.BaseServiceImpl;
import platform.dao.UserDao;
import platform.entity.TokenEntity;
import platform.entity.UserEntity;
import platform.dto.LoginDTO;
import platform.redis.CaptchaRedis;
import platform.service.TokenService;
import platform.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validator.AssertUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserDao, UserEntity> implements UserService {
	@Autowired
	private TokenService tokenService;
	@Autowired
	private CaptchaRedis captchaRedis;

	@Override
	public UserEntity getByMobile(String mobile) {
		return baseDao.getUserByMobile(mobile);
	}

	@Override
	public UserEntity getUserByUserId(Long userId) {
		return baseDao.getUserByUserId(userId);
	}

	@Override
	public Map<String, Object> login(LoginDTO dto) {
		//判断验证码是否正确（到 redis中去查询）
		String captcha = captchaRedis.get(dto.getMobile());
		//验证码是否正确
		if(dto.getCaptcha().equalsIgnoreCase(captcha)){
			UserEntity user = getByMobile(dto.getMobile());
			//如果 user 不存在，自动注册为新用户
			if(user==null){
				user = new UserEntity();
				user.setMobile(dto.getMobile());
				user.setUsername(dto.getMobile());
				user.setCreateDate(new Date());
				insert(user);
			}
			AssertUtils.isNull(user, ErrorCode.ACCOUNT_PASSWORD_ERROR);
			//获取登录token
			TokenEntity tokenEntity = tokenService.createToken(user.getId());
			Map<String, Object> map = new HashMap<>(2);
			map.put("token", tokenEntity.getToken());
			map.put("expire", tokenEntity.getExpireDate().getTime());
			return map;
		}else{
			return null;
		}



	}
}