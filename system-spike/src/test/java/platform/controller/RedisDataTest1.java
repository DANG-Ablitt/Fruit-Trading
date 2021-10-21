package platform.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Redis 集群写入和读取数据测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisDataTest1 {
    //自定义商品ID
    private Long id=1234567899876543210L;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void test(){
        redisTemplate.opsForValue().set(id+"_info","1");
        redisTemplate.opsForValue().set(id+"_count","50");
        System.out.println(redisTemplate.opsForValue().get(id+"_info"));
        System.out.println(redisTemplate.opsForValue().get(id+"_count"));
    }
}
