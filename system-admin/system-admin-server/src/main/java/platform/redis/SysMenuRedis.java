package platform.redis;

import platform.dto.SysMenuDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.RedisKeys;
import redis.RedisUtils;
import utils.HttpContextUtils;

import java.util.List;
import java.util.Set;

/**
 * 菜单管理
 */
@Component
public class SysMenuRedis {
    @Autowired
    private RedisUtils redisUtils;

    public void delete(Long userId){
        //清空菜单导航、权限标识
        redisUtils.deleteByPattern(RedisKeys.getUserMenuNavKey(userId));
        redisUtils.delete(RedisKeys.getUserPermissionsKey(userId));
    }

    public void setUserMenuNavList(Long userId, List<SysMenuDTO> menuList){
        String key = RedisKeys.getUserMenuNavKey(userId, HttpContextUtils.getLanguage());
        redisUtils.set(key, menuList);
    }

    public List<SysMenuDTO> getUserMenuNavList(Long userId){
        String key = RedisKeys.getUserMenuNavKey(userId, HttpContextUtils.getLanguage());
        return (List<SysMenuDTO>)redisUtils.get(key);
    }

    public void setUserPermissions(Long userId, Set<String> permsSet){
        String key = RedisKeys.getUserPermissionsKey(userId);
        redisUtils.set(key, permsSet);
    }

    public Set<String> getUserPermissions(Long userId){
        String key = RedisKeys.getUserPermissionsKey(userId);
        return (Set<String>)redisUtils.get(key);
    }
}
