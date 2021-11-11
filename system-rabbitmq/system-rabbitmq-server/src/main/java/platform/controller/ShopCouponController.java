package platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import platform.client.ProducerClient;
import platform.dto.Message;
import utils.Result;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 商品秒杀
 */
@RestController
@RequestMapping("shop")
public class ShopCouponController {

    @Autowired
    private ProducerClient producerClient;

    //定义交换机
    private String exchangeName = "exchange-2";

    /**
     * 消息ID 暂用单机ID 可使用Redisson的AtomicInteger或者使用相关分布式ID生成策略
     */
    private AtomicInteger messageId = new AtomicInteger(200);

    @PostMapping("send")
    public Result ShopCouponsend(@RequestParam Map<String, Object> params) throws Exception {
        // 发送消息
        // 生成全局唯一ID
        String messageId = String.valueOf(this.messageId.incrementAndGet());
        // 设置消息属性
        Message message = new Message(messageId,exchangeName,"routingKey.demo1",params,0);
        message.setDelayMills(5000);
        // 发送消息
        producerClient.send(message);
        return new Result();
    }

}
