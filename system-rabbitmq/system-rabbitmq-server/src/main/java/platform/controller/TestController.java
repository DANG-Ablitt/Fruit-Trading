package platform.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import platform.client.ProducerClient;
import platform.constant.MessageType;
import platform.dto.Message;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
public class TestController {
    @Autowired
    private ProducerClient producerClient;

    @Value("${exchange.name}")
    private String exchangeName;

    @Value("${delay.exchange.name}")
    private String delayExchangeName;

    /**
     * 消息ID 暂用单机ID 可使用Redisson的AtomicInteger或者使用相关分布式ID生成策略
     */
    private AtomicInteger messageId = new AtomicInteger(200);

    /**
     * 发送可靠性消息demo
     */
    @GetMapping("/produce")
    public String produceMessage() throws Exception {
        String messageId = String.valueOf(this.messageId.incrementAndGet());
        Map<String,Object> attributes = new HashMap<>(2);
        attributes.put("name","张三");
        attributes.put("age","18");
        Message message = new Message(messageId,exchangeName,"routingKey.demo",attributes,0);
        // 发送可靠性消息
        message.setMessageType(MessageType.RELIANT);
        message.setDelayMills(5000);
        producerClient.send(message);

        return "发送单条消息成功";
    }

    /**
     * 发送批量消息demo
     * @return
     */
    @GetMapping("/produceMessages")
    public String produceMessages() throws Exception {
        List<Message> messages = new ArrayList<Message>();
        for(int i=0;i<3;i++){
            String messageId = String.valueOf(this.messageId.incrementAndGet());
            Map<String,Object> attributes = new HashMap<>(2);
            attributes.put("name","张" + (i+1) + "疯");
            attributes.put("age",i);

            Message message =
                    new Message(messageId,exchangeName,"routingKey.demo",attributes,0);

            message.setDelayMills(5000);
            messages.add(message);
        }

        producerClient.send(messages);

        return "发送批量消息成功";
    }

    /**
     * 发送延迟消息demo
     * @return
     */
    @GetMapping("/produceDelayMessage")
    public String produceDelayMessage() throws Exception {
        String messageId = String.valueOf(this.messageId.incrementAndGet());
        Map<String,Object> attributes = new HashMap<>(2);
        attributes.put("name","张三");
        attributes.put("age","18");

        Message message =
                new Message(messageId,delayExchangeName,"delay.demo",attributes,5000);
        // 发送可靠性消息
        message.setMessageType(MessageType.RELIANT);
        producerClient.send(message);

        return "发送单条延迟消息成功";
    }

}
