package platform;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import platform.feign.RabbitMQFeignClient;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQTest {

    @Autowired
    private RabbitMQFeignClient rabbitMQFeignClient;

    @Test
    public void test(){
        Map<String,Object> params = new HashMap<String,Object>();
        // 秒杀商品 id
        params.put("shop_id",1234567890L);
        // 秒杀商品开始时间
        params.put("start_time",1636545600000L);
        // 秒杀商品库存
        params.put("shop_count",999);
        // 与消息中间件通信
        rabbitMQFeignClient.ShopCouponsend(params);
    }
}
