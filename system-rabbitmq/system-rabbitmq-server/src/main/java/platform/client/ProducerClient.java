package platform.client;


import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import platform.broker.RabbitBroker;
import platform.constant.MessageType;
import platform.dto.Message;
import platform.queue.MessageHolder;

import java.util.List;

/**
 *  生产消息实现类
 */
@Component
public class ProducerClient implements MessageProducer {

    @Autowired
    private RabbitBroker rabbitBroker;

    @Override
    public void send(Message message) throws Exception {
        // 校验topic
        Preconditions.checkNotNull(message.getTopic());
        // 根据消息类型采用不同的策略
        String messageType = message.getMessageType();
        switch (messageType) {
            case MessageType.RAPID:
                rabbitBroker.rapidSend(message);
                break;
            case MessageType.CONFIRM:
                rabbitBroker.confirmSend(message);
                break;
            case MessageType.RELIANT:
                rabbitBroker.reliantSend(message);
                break;
            default:
                break;
        }
    }

    @Override
    public void send(List<Message> messages) throws Exception {
        messages.forEach( message -> {
            // 传输迅速消息
            message.setMessageType(MessageType.RAPID);
            MessageHolder.add(message);
        });
        rabbitBroker.sendMessages();
    }

    @Override
    public void send(Message message, SendCallback sendCallback) throws Exception {

    }

}
