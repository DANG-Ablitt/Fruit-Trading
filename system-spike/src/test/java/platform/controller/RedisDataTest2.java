package platform.controller;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Redis 集群分布式锁的可用性测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisDataTest2 {
    //自定义商品ID
    private Long id=1234567899876543210L;

    @Autowired
    private RedissonClient redisson;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //商品库存 100
    //private int count=10;
    @Test
    public void test() throws InterruptedException {
        //创建N个线程
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 19; i++) {
            //Thread.sleep(10);
            cachedThreadPool.execute(new Runnable() {
                @SneakyThrows
                public void run() {
                    //创建锁
                    RLock Lock = redisson.getLock(id+"_lock");
                    Lock.lock(300, TimeUnit.SECONDS);
                    Integer count=Integer.valueOf(redisTemplate.opsForValue().get(id+"_count"));
                    try{
                        if(count>0){
                            redisTemplate.opsForValue().set(id+"_count", String.valueOf(count - 1));
                            //--count;
                        }
                    }finally {
                        //释放锁
                        Lock.unlock();
                    }
                }
            });
        }
        //不再接受新的任务
        cachedThreadPool.shutdown();
        while (true) {
            //手动循环确实效率很低，不推荐
            if (cachedThreadPool.isTerminated()) {
                System.out.println("程序运行结束");
                break;
            }
        }
    }
}
