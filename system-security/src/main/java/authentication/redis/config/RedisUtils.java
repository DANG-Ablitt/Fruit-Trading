package authentication.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**  默认过期时长为24小时，单位：秒 */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24L;
    /**  过期时长为1小时，单位：秒 */
    public final static long HOUR_ONE_EXPIRE = 60 * 60 * 1L;
    /**  过期时长为6小时，单位：秒 */
    public final static long HOUR_SIX_EXPIRE = 60 * 60 * 6L;
    /**  不设置过期时长 */
    public final static long NOT_EXPIRE = -1L;

    /**
     * set方法
     * @param key 键
     * @param value 值
     * @param expire 过期时间
     */
    public void set(String key, Object value, long expire){
        redisTemplate.opsForValue().set(key, value);
        if(expire != NOT_EXPIRE){
            expire(key, expire);
        }
    }

    /**
     * 传统set方法 不设置过期时间
     */
    public void set(String key, Object value){
        set(key, value, DEFAULT_EXPIRE);
    }

    /**
     * get方法
     * @param key 键
     * @param expire 过期时间
     */
    public Object get(String key, long expire) {
        Object value = redisTemplate.opsForValue().get(key);
        if(expire != NOT_EXPIRE){
            expire(key, expire);
        }
        return value;
    }

    /**
     * 传统get方法 不设置过期时间
     */
    public Object get(String key) {
        return get(key, NOT_EXPIRE);
    }

    /**
     * 模糊查找
     */
    public Set<String> keys(String pattern){
        return redisTemplate.keys(pattern);
    }

    /**
     * 批量删除模糊查找到的数据
     */
    public void deleteByPattern(String pattern) {
        redisTemplate.delete(keys(pattern));
    }

    /**
     * 根据key删除
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量删除
     */
    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 获取单个 Hash类型数据
     * @param key 键
     * @param field
     */
    public Object hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    public Map<String, Object> hGetAll(String key){
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries(key);
    }

    public void hMSet(String key, Map<String, Object> map){
        hMSet(key, map, DEFAULT_EXPIRE);
    }

    public void hMSet(String key, Map<String, Object> map, long expire){
        redisTemplate.opsForHash().putAll(key, map);

        if(expire != NOT_EXPIRE){
            expire(key, expire);
        }
    }

    public void hSet(String key, String field, Object value) {

        hSet(key, field, value, DEFAULT_EXPIRE);
    }

    public void hSet(String key, String field, Object value, long expire) {
        redisTemplate.opsForHash().put(key, field, value);

        if(expire != NOT_EXPIRE){
            expire(key, expire);
        }
    }

    public void expire(String key, long expire){

        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    public void hDel(String key, Object... fields){

        redisTemplate.opsForHash().delete(key, fields);
    }

    public void leftPush(String key, Object value){

        leftPush(key, value, DEFAULT_EXPIRE);
    }

    public void leftPush(String key, Object value, long expire){
        redisTemplate.opsForList().leftPush(key, value);

        if(expire != NOT_EXPIRE){
            expire(key, expire);
        }
    }

    public Object rightPop(String key){

        return redisTemplate.opsForList().rightPop(key);
    }
}