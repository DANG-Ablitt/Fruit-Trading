package platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import platform.client.ProducerClient;
import platform.dto.Message;
import utils.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 短信发送
 */
@RestController
@RequestMapping("message")
public class MessageController {

    @Autowired
    private ProducerClient producerClient;

    //定义交换机
    private String exchangeName = "exchange-1";

    /**
     * 消息ID 暂用单机ID 可使用Redisson的AtomicInteger或者使用相关分布式ID生成策略
     */
    private AtomicInteger messageId = new AtomicInteger(200);

    /**
     * 短信批量发送
     */
    @PostMapping("send")
    public Result Messagesend(@RequestParam Map<String, Object> params) throws Exception {
        //批量消息列表
        List<Message> messages = new ArrayList<Message>();
        //遍历Map集合（params）
        params.forEach((key, value) -> {
            //生成全局唯一ID
            String messageId = String.valueOf(this.messageId.incrementAndGet());
            //封装消息参数（会员手机号）
            Map<String,Object> attributes = new HashMap<>(2);
            attributes.put("iphone",value.toString());
            //设置消息属性
            Message message = new Message(messageId,exchangeName,"routingKey.demo",attributes,0);
            message.setDelayMills(5000);
            //添加到批量消息列表
            messages.add(message);
        });
        //发送批量消息
        producerClient.send(messages);
        return new Result<>();
    }

}
