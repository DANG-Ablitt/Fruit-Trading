package authentication.redis.detailredis;


import authentication.redis.config.RedisKeys;
import authentication.redis.config.RedisUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import security.enums.UserKillEnum;
import security.user.UserDetail;
import java.util.Map;

/**
 * 用户Redis
 */

@Component
public class UserDetailRedis {
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 保存用户信息到redis
     * @param user  用户信息
     * @param expire 过期时间
     */
    public void set(UserDetail user, long expire){
        if(user == null){
            return ;
        }
        String key = RedisKeys.getSecurityUserKey(user.getId());
        //bean to map
        user.setKill(UserKillEnum.NO.value());
        Map<String, Object> map = BeanUtil.beanToMap(user, false, true);
        redisUtils.hMSet(key, map, expire);
    }

    /**
     * 从 redis获取用户信息
     */
    public UserDetail getUser(Long id){
        String key = RedisKeys.getSecurityUserKey(id);
        Map<String, Object> map = redisUtils.hGetAll(key);
        if(MapUtil.isEmpty(map)){
            return null;
        }
        //map to bean
        UserDetail user = BeanUtil.mapToBean(map, UserDetail.class, true);
        return user;
    }

    /**
     * 用户退出登录
     */
    public void logout(Long id){
        String key = RedisKeys.getSecurityUserKey(id);
        redisUtils.hSet(key, "kill", UserKillEnum.YES.value());
    }
}