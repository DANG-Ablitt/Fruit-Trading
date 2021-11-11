package platform.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import platform.dto.Message;
import java.util.Map;

@Slf4j
@Component
public class TestConsumer {
    /**
     * @RabbitListener是一个功能强大的注解。
     * 这个注解里面可以注解配置@QueueBinding、@Queue、@Exchange直接通过
     * 这个组合注解一次性搞定多个交换机、绑定、路由、并且配置监听功能等
     *
     * 如下边例子：
     *  使用@RabbitListener自动创建队列，并对Exchange和Queue进行绑定
     *  //将交换机和队列绑定
     *  @RabbitListener(bindings = @QueueBinding(
     *              //声明队列
     *              //autoDelete表示没有消费者之后队列是否自动删除
     *             value = @Queue(value = "${queue.value}",
     *                     durable = "${queue.durable}"),
     *             //声明交换机
     *             exchange = @Exchange(name = "${exchange.name}",
     *                     //是否持久化
     *                     durable = "${exchange.durable}",
     *                     //交换机类型
     *                     type = "${exchange.type}",
     *                     //忽略声明异常
     *                     ignoreDeclarationExceptions = "${exchange.ignoreDeclarationExceptions}"),
     *                     //在topic方式下，这个就是 routingKey
     *                     key = "${key}"))
     *
     * 配置可以转移到配置文件中而不要在代码里写死,然后使用"${参数名}"的方式
     * durable是否持久化
     */
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
        log.info("【消费消息】:{}", message);
        // 2.处理成功后,获取deliveryTag并进行手工ack签收
        // 原因:配置文件acknowledge-mode为manual
        Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        // 手工ack签收 批量写false
        // 重点:如果不签收,则消息会从ready变为uncheck状态,mq会再次发送这条消息
        channel.basicAck(deliveryTag,false);

    }


    /**
     * 配置可以转移到配置文件中而不要在代码里写死,然后使用"${参数名}"的方式
     * durable是否持久化
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${delay.queue.value}",
                    durable = "${delay.queue.durable}"),
            exchange = @Exchange(name = "${delay.exchange.name}",
                    durable = "${delay.exchange.durable}",
                    type = "${delay.exchange.type}",
                    ignoreDeclarationExceptions = "${delay.exchange.ignoreDeclarationExceptions}"),
                    key = "${delay.key}"))
    @RabbitHandler
    /**
     * 使用 @Payload 和 @Headers 注解可以获取消息中的 body 和 headers 消息。
     * 它们都会被 MessageConvert 转换器解析转换后(使用 fromMessage 方法进行转换)，
     * 将结果绑定在对应注解的方法中。
     */
    public void onDelayMessage(@Payload Message message,
                               @Headers Map<String,Object> headers,
                               Channel channel) throws Exception{

        // 1.收到消息之后进行业务端消费处理
        log.info("【消费延迟消息】:{}", message);

        // 2.处理成功后,获取deliveryTag并进行手工ack签收
        // 原因:配置文件acknowledge-mode为manual
        Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        // 手工ack签收 批量写false
        // 重点:如果不签收,则消息会从ready变为uncheck状态,mq会再次发送这条消息
        channel.basicAck(deliveryTag,false);
    }
}
