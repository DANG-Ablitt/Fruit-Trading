package authentication.filter.jwtservice;

import authentication.dao.staff.SysStaffDao;
import authentication.dto.AuthorizationDTO;
import authentication.entity.SysStaffEntity;
import authentication.jwt.JwtProperties;
import authentication.jwt.JwtUtils;
import authentication.redis.detailredis.StaffDetailRedis;
import authentication.service.CaptchaService;
import authentication.service.SysResourceService;
import authentication.service.SysRoleDataScopeService;
import authentication.tools.Result;
import exception.ErrorCode;
import exception.RenException;
import log.SysLogLogin;
import log.enums.LogTypeEnum;
import log.enums.LoginOperationEnum;
import log.enums.LoginStatusEnum;
import log.producer.LogProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import security.bo.ResourceBO;
import security.enums.UserKillEnum;
import security.password.PasswordUtils;
import security.user.StaffDetail;
import utils.ConvertUtils;
import utils.HttpContextUtils;
import utils.IpUtils;
import utils.SpringContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public class NamePassLogin implements JwtService {
    //@Autowired
    //private CaptchaService captchaService;
    //@Autowired
    //private LogProducer logProducer;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private StaffDetailRedis staffDetailRedis;
    //@Autowired
    //private SysRoleDataScopeService sysRoleDataScopeService;
    //@Autowired
    //private SysResourceService sysResourceService;
    //@Autowired
    //private SysStaffDao sysStaffDao;
    /**
     *手机号+密码+图形验证码登录详细认证过程
     */
    @Override
    public Authentication loadUserByUsername(Authentication authentication) throws Exception {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //验证码是否正确
        String captcha=(String) authentication.getDetails();
        //public boolean validate(String uuid, String code)
        //从缓存中根据uuid获取验证码并效验
        boolean flag = new CaptchaService().validate((String) authentication.getPrincipal(), captcha);
        if(!flag){
            return new Result<AuthorizationDTO>().error(ErrorCode.CAPTCHA_ERROR);
        }
        //获取职员信息（从数据库同步）
        //获取基本信息
        //Object pp=SpringContextUtils.getBean(SysStaffDao.class);
        SysStaffEntity staff1= SpringContextUtils.getBean(SysStaffDao.class).getByUsername(authentication.getName());;
        StaffDetail staff = ConvertUtils.sourceToTarget(staff1, StaffDetail.class);
        //初始化用户数据
        staff =initUserData(staff);
        //登录日志
        //SysLogLogin log = new SysLogLogin();
        //log.setType(LogTypeEnum.LOGIN.value());
        //log.setOperation(LoginOperationEnum.LOGIN.value());
        //log.setCreateDate(new Date());
        //log.setIp(IpUtils.getIpAddr(request));
        //log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        //log.setIp(IpUtils.getIpAddr(request));
        //账号不存在
        if(staff == null){
            //log.setStatus(LoginStatusEnum.FAIL.value());
            //log.setCreatorName(authentication.getName());
            //logProducer.saveLog(log);
            throw new RenException(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }
        //密码错误
        if(!PasswordUtils.matches((String) authentication.getCredentials(), staff.getPassword())){
            //log.setStatus(LoginStatusEnum.FAIL.value());
            //log.setCreator(staff.getId());
            //log.setCreatorName(staff.getUsername());
            //logProducer.saveLog(log);
            throw new RenException(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }

        //账号停用
        if(staff.getStatus() == UserKillEnum.NO.value()){
            //log.setStatus(LoginStatusEnum.LOCK.value());
            //log.setCreator(staff.getId());
            //log.setCreatorName(staff.getUsername());
            //logProducer.saveLog(log);
            throw new RenException(ErrorCode.ACCOUNT_DISABLE);
        }
        Object pul_class1=SpringContextUtils.getBean(JwtProperties.class);
        Object pul_class2=SpringContextUtils.getBean(JwtUtils.class);
        //保存到缓存（如果缓存不存在）
        //staffDetailRedis.set(staff, jwtProperties.getExpire());
        //生成token
        String token = SpringContextUtils.getBean(JwtUtils.class).generateToken(staff.getId());
        //保存授权信息并返回
        AuthorizationDTO authorization1 = new AuthorizationDTO();
        authorization1.setToken(token);
        authorization1.setToken_expire(SpringContextUtils.getBean(JwtProperties.class).getExpire());
        //登录用户信息
        //log.setCreator(staff.getId());
        //log.setCreatorName(staff.getUsername());
        //log.setStatus(LoginStatusEnum.SUCCESS.value());
        //logProducer.saveLog(log);
        return new Result<AuthorizationDTO>().ok(authorization1);
    }

    /**
     * 初始化用户数据
     */
    private StaffDetail initUserData(StaffDetail staffDetail) throws Exception {
        if(staffDetail == null){
            return null;
        }
        //用户部门数据权限
        List<Long> deptIdList = SpringContextUtils.getBean(SysRoleDataScopeService.class).getDataScopeList(staffDetail.getId());
        staffDetail.setDeptIdList(deptIdList);
        //用户资源列表
        List<ResourceBO> resourceList = SpringContextUtils.getBean(SysResourceService.class).getUserResourceList(staffDetail.getId());
        staffDetail.setResourceList(resourceList);
        return staffDetail;
    }
}
