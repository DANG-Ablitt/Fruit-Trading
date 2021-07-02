package platform.service.impl;

import exception.ErrorCode;
import exception.RenException;
import log.SysLogLogin;
import log.enums.LogTypeEnum;
import log.enums.LoginOperationEnum;
import log.enums.LoginStatusEnum;
import log.producer.LogProducer;
import platform.dto.AuthorizationDTO;
import platform.dto.LoginDTO;
import platform.enums.UserStatusEnum;
import platform.feign.UserFeignClient;
import platform.jwt.JwtProperties;
import platform.jwt.JwtUtils;
import platform.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import redis.StaffDetailRedis;
import security.password.PasswordUtils;
import security.user.SecurityStaff;
import security.user.StaffDetail;
import utils.HttpContextUtils;
import utils.IpUtils;
import utils.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 认证服务
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private StaffDetailRedis staffDetailRedis;
    @Autowired
    private LogProducer logProducer;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public AuthorizationDTO login(LoginDTO login) {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();

        //获取用户信息
        Result<StaffDetail> result = userFeignClient.getByUsername(login.getUsername());
        StaffDetail user = result.getData();

        //登录日志
        SysLogLogin log = new SysLogLogin();
        log.setType(LogTypeEnum.LOGIN.value());
        log.setOperation(LoginOperationEnum.LOGIN.value());
        log.setCreateDate(new Date());
        log.setIp(IpUtils.getIpAddr(request));
        log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        log.setIp(IpUtils.getIpAddr(request));

        //账号不存在
        if(user == null){
            log.setStatus(LoginStatusEnum.FAIL.value());
            log.setCreatorName(login.getUsername());
            logProducer.saveLog(log);
            throw new RenException(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }

        //密码错误
        if(!PasswordUtils.matches(login.getPassword(), user.getPassword())){
            log.setStatus(LoginStatusEnum.FAIL.value());
            log.setCreator(user.getId());
            log.setCreatorName(user.getUsername());
            logProducer.saveLog(log);
            throw new RenException(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }

        //账号停用
        if(user.getStatus() == UserStatusEnum.DISABLE.value()){
            log.setStatus(LoginStatusEnum.LOCK.value());
            log.setCreator(user.getId());
            log.setCreatorName(user.getUsername());
            logProducer.saveLog(log);
            throw new RenException(ErrorCode.ACCOUNT_DISABLE);
        }
        //保存到Redis
        staffDetailRedis.set(user, jwtProperties.getExpire());
        //登录成功，生成token
        String token = jwtUtils.generateToken(user.getId());
        //授权信息
        AuthorizationDTO authorization = new AuthorizationDTO();
        authorization.setToken(token);
        authorization.setExpire(jwtProperties.getExpire());
        //登录用户信息
        log.setCreator(user.getId());
        log.setCreatorName(user.getUsername());
        log.setStatus(LoginStatusEnum.SUCCESS.value());
        logProducer.saveLog(log);
        return authorization;
    }

    @Override
    public void logout(Long userId) {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        StaffDetail user = SecurityStaff.getStaff();
        //退出日志
        SysLogLogin log = new SysLogLogin();
        log.setType(LogTypeEnum.LOGIN.value());
        log.setOperation(LoginOperationEnum.LOGOUT.value());
        log.setIp(IpUtils.getIpAddr(request));
        log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        log.setIp(IpUtils.getIpAddr(request));
        log.setStatus(LoginStatusEnum.SUCCESS.value());
        log.setCreator(user.getId());
        log.setCreatorName(user.getUsername());
        log.setCreateDate(new Date());
        logProducer.saveLog(log);
        staffDetailRedis.logout(userId);
    }

}