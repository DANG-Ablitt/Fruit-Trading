package platform.service.impl;

import constant.Constant;
import enums.SuperAdminEnum;
import exception.ErrorCode;
import exception.RenException;
import io.jsonwebtoken.Claims;
import platform.feign.ResourceFeignClient;
import platform.feign.UserFeignClient;
import platform.jwt.JwtUtils;
import platform.service.ResourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import redis.StaffDetailRedis;
import security.bo.ResourceBO;
import security.enums.ResourceAuthEnum;
import security.enums.UserKillEnum;
import security.user.StaffDetail;
import utils.Result;

import java.util.List;

/**
 * 资源服务
 */
@Service
public class ResourceServiceImpl implements ResourceService {
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private ResourceFeignClient resourceFeignClient;
    @Autowired
    private StaffDetailRedis staffDetailRedis;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public StaffDetail resource(String token, String url, String method) {
        //1、获取所有资源列表
        List<ResourceBO> resourceList = resourceFeignClient.list();
        //2、判断是否在资源列表里
        ResourceBO resource = pathMatcher(url, method, resourceList);
        //3、无需登录认证
        if(resource != null && resource.getAuthLevel() == ResourceAuthEnum.NO_AUTH.value()){
            return null;
        }
        //4、获取用户信息(利用token)
        StaffDetail userDetail = getStaffDetail(token);
        //5、登录认证
        if(resource != null && resource.getAuthLevel() == ResourceAuthEnum.LOGIN_AUTH.value()){
            return userDetail;
        }
        //6、不在资源列表里，只要登录了，就有权限访问
        if(resource == null){
            return userDetail;
        }
        //7、当前登录用户是超级管理员
        if(userDetail.getSuperAdmin() == SuperAdminEnum.YES.value()){
            return userDetail;
        }
        //8、需要鉴权，获取用户资源列表
        List<ResourceBO> userResourceList = userDetail.getResourceList();
        //9、如果不在用户资源列表里，则无权访问
        resource = pathMatcher(url, method, userResourceList);
        if(resource != null){
            return userDetail;
        }

        throw new RenException(ErrorCode.FORBIDDEN);
    }

    /**
     * 根据token，获取用户信息
     * @param token   用户token
     * @return    返回用户信息
     */
    private StaffDetail getStaffDetail(String token) {
        //token为空
        if(StringUtils.isBlank(token)){
            throw new RenException(ErrorCode.NOT_NULL, Constant.TOKEN_HEADER);
        }
        //是否过期
        Claims claims = jwtUtils.getClaimByToken(token);
        if(claims == null || jwtUtils.isTokenExpired(claims.getExpiration())){
            throw new RenException(ErrorCode.UNAUTHORIZED);
        }
        //获取用户ID
        Long userId = Long.parseLong(claims.getSubject());
        //查询Redis，如果没数据，则保持用户信息到Redis
        StaffDetail userDetail = staffDetailRedis.get(userId);
        if(userDetail == null){
            //获取用户信息（从数据库）
            Result<StaffDetail> result = userFeignClient.getById(userId);
            userDetail = result.getData();
            if(userDetail == null){
                throw new RenException(ErrorCode.ACCOUNT_NOT_EXIST);
            }
            //过期时间
            long expire = (claims.getExpiration().getTime() - System.currentTimeMillis())/1000;
            staffDetailRedis.set(userDetail, expire);
        }
        //Redis有数据，则判断是否被踢出，如果被踢出，则提示重新登录
        if(userDetail.getKill() == UserKillEnum.YES.value()){
            throw new RenException(ErrorCode.UNAUTHORIZED);
        }
        return userDetail;
    }

    private ResourceBO pathMatcher(String url, String method, List<ResourceBO> resourceList){
        for (ResourceBO resource : resourceList) {
            if (StringUtils.isNotBlank(resource.getResourceUrl()) && antPathMatcher.match(resource.getResourceUrl(), url)
                    && method.equalsIgnoreCase(resource.getResourceMethod())) {
                return resource;
            }
        }
        return null;
    }
}