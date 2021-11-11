package platform.consumer;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import platform.dto.Message;
import platform.dto.ScheduleJobDTO;
import platform.feign.JopFeignClient;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 商品秒杀
 */
@Component
public class ShopCouponConsumer {

    @Autowired
    private JopFeignClient jopFeignClient;

    // 12小时
    private static final Long TIME_12 = 1000*60*60*12*1L;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-2",
                    durable = "true"),
            exchange = @Exchange(name = "exchange-2",
                    durable = "true",
                    type = "topic",
                    ignoreDeclarationExceptions = "true"),
            key = "routingKey.demo1"))
    @RabbitHandler
    public void onMessage(@Payload Message message,
                          @Headers Map<String,Object> headers,
                          Channel channel) throws Exception{
        // 1.收到消息之后进行业务端消费处理
        // Map 转 JSON
        Map<String,Object> params = message.getAttributes();
        // 发送标志
        Integer flag = Integer.valueOf((String) params.get("flag"));
        // 秒杀商品 id
        Long shop_id = Long.valueOf((String)params.get("shop_id"));
        // 秒杀商品开始时间
        Long start_time = Long.valueOf((String)params.get("start_time"));
        // 如果发送标志为1
        if (flag == 1){
            // 秒杀开始前12小时 由定时任务将秒杀数据写入redis
            Integer shop_count = Integer.valueOf((String) params.get("shop_count"));
            // 秒杀商品库存
            //重新封装参数
            Map<String,Object> params1 = new HashMap<>();
            params1.put("shop_id",shop_id);
            params1.put("shop_count",shop_count);
            String json= JSON.toJSONString(params1);
            // 提前12小时将商品秒杀数据写入 redis
            Long time = start_time - TIME_12;
            //生成 cron 表达式
            Date date = new Date(time);
            // 年
            int year=date.getYear() + 1900;
            // 月
            int month=date.getMonth() + 1;
            // 日
            int data=date.getDate();
            // 时
            int hours = date.getHours();
            // 分
            int minutes = date.getMinutes();
            // 秒
            int seconds = date.getSeconds();
            String cron1=seconds+" "+minutes+" "+hours+" "+data+" "+month+" "+"?"+" "+year;
            //与定时任务模块通信
            jopFeignClient.save(
                    new ScheduleJobDTO("shopRedisTask",json,cron1,
                            1,"秒杀开始前12小时将秒杀数据写入redis"));
        }
        //启动定时任务 在秒杀服务开始时将状态标记开启
        //生成 cron 表达式
        Date stater_date = new Date(start_time);
        // 年
        int stater_year=stater_date.getYear() + 1900;
        // 月
        int stater_month=stater_date.getMonth() + 1;
        // 日
        int stater_data=stater_date.getDate();
        // 时
        int stater_hours = stater_date.getHours();
        // 分
        int stater_minutes = stater_date.getMinutes();
        // 秒
        int stater_seconds = stater_date.getSeconds();
        String cron2=stater_seconds+" "+stater_minutes+" "+stater_hours+" "+stater_data+" "+stater_month+" "+"?"+" "+stater_year;
        //与定时任务模块通信
        jopFeignClient.save(
                new ScheduleJobDTO("shopInfoRedisTask",shop_id.toString(),cron2,
                        1,"在秒杀服务开始时将状态标记开启"));
        // 2.处理成功后,获取deliveryTag并进行手工ack签收
        // 原因:配置文件acknowledge-mode为manual
        Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        // 手工ack签收 批量写false
        // 重点:如果不签收,则消息会从ready变为uncheck状态,mq会再次发送这条消息
        channel.basicAck(deliveryTag,false);
    }
}
