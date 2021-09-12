package platform.confirm;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import platform.constant.BrokerMessageStatus;
import platform.constant.MessageType;
import java.util.Date;
import java.util.List;

/**
 * RabbitMQ对生产端发送消息的ack
 */
@Component
@Slf4j
public class ConfirmCallback implements RabbitTemplate.ConfirmCallback {

    //@Autowired
    //private BrokerMessageDao brokerMessageDao;

    /**
     * guava splitter：按#分割
     */
    private Splitter splitter = Splitter.on("#");

    /**
     * 无论是 confirm（确认） 消息 还是 reliant（可靠） 消息 ，
     * 发送消息以后 broker都会去回调 confirm
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData.getId();
        if(StrUtil.isBlank(id)){
            log.error("send message content is empty");
            return ;
        }
        // 	具体的消息应答
        List<String> strings = splitter.splitToList(id);
        String messageId = strings.get(0);
        long sendTime = Long.parseLong(strings.get(1));
        String messageType = strings.get(2);
        if(ack) {
            //	当Broker 返回ACK成功时, 就是更新一下日志表里对应的消息发送状态为 SEND_OK
            // 	如果当前消息类型为reliant 我们就去数据库查找并进行更新
            if(MessageType.RELIANT.endsWith(messageType)) {
                /*BrokerMessage bm = BrokerMessage.builder()
                        .messageId(messageId)
                        .status(BrokerMessageStatus.SEND_OK.getStatus())
                        .updateTime(new Date()).build();
                brokerMessageDao.updateByPrimaryKeySelective(bm);*/
            }
            log.info("send message is OK, confirm messageId: {}, sendTime: {}", messageId, sendTime);
        } else {
            log.error("send message is Fail, confirm messageId: {}, sendTime: {}", messageId, sendTime);
            // 队列满了/
        }
    }

}
