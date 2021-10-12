package platform.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import platform.dto.Message;
import platform.feign.MessageFeignClient;
import security.user.StaffDetail;
import utils.Result;

import java.util.Map;

/**
 * 短信发送消费者
 */
@Component
public class MessageConsumer {

    @Autowired
    private MessageFeignClient messageFeignClient;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${queue.value}",
                    durable = "${queue.durable}"),
            exchange = @Exchange(name = "${exchange.name}",
                    durable = "${exchange.durable}",
                    type = "${exchange.type}",
                    ignoreDeclarationExceptions = "${exchange.ignoreDeclarationExceptions}"),
            key = "${key}"))
    @RabbitHandler
    public void onMessage(@Payload Message message,
                          @Headers Map<String,Object> headers,
                          Channel channel) throws Exception{
        // 1.收到消息之后进行业务端消费处理
        // 从消息中获取会员手机号
        String iphone=(String) message.getAttributes().get("iphone");
        // 通过 Feign 和消息模块通信，发送短信
        Result result=messageFeignClient.send(iphone,null);
        //log.info("【消费消息】:{}", message);
        // 2.处理成功后,获取deliveryTag并进行手工ack签收
        // 原因:配置文件acknowledge-mode为manual
        Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        // 手工ack签收 批量写false
        // 重点:如果不签收,则消息会从ready变为uncheck状态,mq会再次发送这条消息
        channel.basicAck(deliveryTag,false);

    }
}
