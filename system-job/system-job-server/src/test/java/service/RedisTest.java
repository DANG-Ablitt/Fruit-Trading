package service;

import cn.hutool.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import platform.JobApplication;
import platform.service.ScheduleJobService;
import redis.RedisUtils;
import utils.SpringContextUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=JobApplication.class)
public class RedisTest {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test(){

        // 秒杀商品 id
        Long shop_id = 123456L;
        // 存入 redis 集群
        redisUtils.set(Long.toString(shop_id)+"_info",1);
        // 秒杀商品库存
        Integer shop_count = 666;
        // 存入 redis 集群
        redisUtils.set(Long.toString(shop_id)+"_count",shop_count);
        // 用户集合
        redisTemplate.opsForSet().add(Long.toString(shop_id)+"_list","");
    }
}
