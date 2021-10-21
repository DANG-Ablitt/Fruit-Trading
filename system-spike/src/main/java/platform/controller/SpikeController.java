package platform.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.google.common.util.concurrent.RateLimiter;
import exception.RenException;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import platform.dto.SpikeDTO;
import platform.entity.TokenEntity;
import platform.service.TokenService;
import redis.RedisUtils;
import utils.Result;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 秒杀服务专用
 * 分布式锁：商品id+"_lock"
 * 商品库存：商品id+"_count"
 * 商品状态：商品id+"_info"
 * 用户集合：商品id+"_list"
 * 用户标识：商品id
 * 接口重复调用：商品id+token
 */
@RestController
@RequestMapping("spike")
public class SpikeController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedissonClient redisson;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private TokenService tokenService;
    /**
     * 秒杀前的限流
     * 每秒钟只发出10个令牌，拿到令牌的请求才可以进入秒杀过程
     * 使用了Google guava的RateLimiter
     */
    private RateLimiter spikeRateLimiter = RateLimiter.create(10);
    //md5盐值字符串,用于混淆MD5
    private final String salt = "asdvbhn*&gh&^%gr%fdfdcvd&^^%sds%*";
    //获取用户 id
    Long user_id;

    @PostMapping
    public Result Info(@RequestBody SpikeDTO dto){
        //1.检查接口是否重复调用
        if(redisTemplate.opsForValue().get(dto.getId()+dto.getToken()).equals("1")){
            //如果在1min内重复调用，执行返回操作
            return new Result();
        }
        //如果不是接口重复调用，将接口标识写入Redis
        redisTemplate.opsForValue().set(dto.getId()+dto.getToken(),"1",60,TimeUnit.SECONDS);
        //2.检查秒杀活动是否开始（状态标志是否置1）
        if(Integer.valueOf(redisTemplate.opsForValue().get(dto.getId()+"info"))!=1){
            //如果秒杀未在进行中，执行返回操作
            return new Result();
        }
        //3.限流
        if(!spikeRateLimiter.tryAcquire()){
            //如果被限流，执行返回操作
            return new Result();
        }
        //4.安全性检查（检查数据有没有被篡改）
        if(dto.getSign()==null||!getMD5(dto.getId(),dto.getToken()).equals(dto.getSign())){
            //如果数据被篡改，执行返回操作
            return new Result();
        }
        //5.效验 Token 并获取用户id
        //token是否为空
        if(StringUtils.isBlank(dto.getToken())){
            //如果token为空，执行返回操作
            return new Result();
        }
        //token 是否过期
        if(getToken(dto.getToken())){
            //如果token过期，执行返回操作
            return new Result();
        }
        //6.判断用户是否已经参与过秒杀活动
        if(!redisTemplate.opsForSet().isMember("11","12")){
            return new Result();
        }
        //7.获取分布式锁
        RLock Lock = redisson.getLock(dto.getId()+"lock");
        //设置分布式锁的过期时间
        Lock.lock(3, TimeUnit.SECONDS);
        try {
            //从缓存中读取商品库存数量
            Integer count = Integer.valueOf(redisTemplate.opsForValue().get(dto.getId()+"count"));
            //有货
            if (count > 0) {
                //8.库存减一
                redisTemplate.opsForValue().set(dto.getId()+"count", String.valueOf(count - 1));
                //将用户id存储在 Redis中
                redisTemplate.opsForSet().add("11", String.valueOf(user_id));
               //9.后续处理由消息中间件完成

            }
        } finally {
            //10.释放分布式锁
            Lock.unlock();
        }
        return new Result();
    }

    /**
     * md5 加密（对商品id和用户token重新加密检查数据有没有被篡改）
     */
    private String getMD5(long Id,String token) {
        String base = Id + token+"/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * 效验 TOKEN
     */
    private Boolean getToken(String token){
        //检查token关联信息是否在 redis 中
        Map<String, Object> map = redisUtils.hGetAll(token);
        if(MapUtil.isEmpty(map)||getToken_time(map)){
            //从数据库加载token关联信息
            TokenEntity tokenEntity=tokenService.getByToken(token);
            //判断token是否过期
            if(tokenEntity.getExpireDate().getTime() < System.currentTimeMillis()){
                //过期 返回true
                return true;
            }
            //没有过期 写入redis
            redisUtils.hMSet(token, BeanUtil.beanToMap(tokenEntity, false, true), 3600*12);
            //获取用户id
            user_id=tokenEntity.getUserId();
            return false;
        }
        //判断token是否过期
        if(!getToken_time(map)){
            //从集合中获取用户id
           user_id=(Long) map.get("userId");
           return false;
        }
        return true;
    }

    /**
     * 效验 redis 中的 token 是否过期
     */
    private Boolean getToken_time(Map<String, Object> map){
        //获取token的过期时间
        Date data1=(Date)map.get("expireDate");
        if(data1.getTime()<System.currentTimeMillis()){
            return true;
        }
        return false;
    }
}