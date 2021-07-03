package platform.redis;

import platform.entity.SysResourceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.RedisKeys;
import redis.RedisUtils;

import java.util.List;

/**
 * 资源管理
 */
@Component
public class SysResourceRedis {
    @Autowired
    private RedisUtils redisUtils;

    public void delete() {
        String key = RedisKeys.getSysResourceKey();
        redisUtils.delete(key);
    }

    public void set(List<SysResourceEntity> list){
        String key = RedisKeys.getSysResourceKey();
        redisUtils.set(key, list);
    }

    public List<SysResourceEntity> get(){
        String key = RedisKeys.getSysResourceKey();
        return (List<SysResourceEntity>)redisUtils.get(key);
    }

}