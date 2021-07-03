package redis;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import security.enums.UserKillEnum;
import security.user.StaffDetail;
import java.util.Map;

/**
 * 用户Redis
 */
@Component
public class StaffDetailRedis {
    @Autowired
    private RedisUtils redisUtils;

    public void set(StaffDetail user, long expire){
        if(user == null){
            return ;
        }
        String key = RedisKeys.getSecurityUserKey(user.getId());
        //bean to map
        user.setKill(UserKillEnum.NO.value());
        Map<String, Object> map = BeanUtil.beanToMap(user, false, true);
        redisUtils.hMSet(key, map, expire);

        //用户登录时，清空菜单导航、权限标识
        redisUtils.delete(RedisKeys.getUserMenuNavKey(user.getId()));
        redisUtils.delete(RedisKeys.getUserPermissionsKey(user.getId()));
    }

    public StaffDetail get(Long id){
        String key = RedisKeys.getSecurityUserKey(id);

        Map<String, Object> map = redisUtils.hGetAll(key);
        if(MapUtil.isEmpty(map)){
            return null;
        }

        //map to bean
        StaffDetail user = BeanUtil.mapToBean(map, StaffDetail.class, true);

        return user;
    }

    /**
     * 用户退出
     * @param id  用户ID
     */
    public void logout(Long id){
        String key = RedisKeys.getSecurityUserKey(id);
        redisUtils.hSet(key, "kill", UserKillEnum.YES.value());

        //清空菜单导航、权限标识
        redisUtils.deleteByPattern(RedisKeys.getUserMenuNavKey(id));
        redisUtils.delete(RedisKeys.getUserPermissionsKey(id));
    }
}