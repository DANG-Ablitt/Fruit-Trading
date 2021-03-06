package platform.broker;

import platform.dto.Message;

/**
 * 具体发送不同种类型消息的接口
 */
public interface RabbitBroker {

    /**
     * 发送迅速消息
     */
    void rapidSend(Message message);

    /**
     * 发送确认消息
     */
    void confirmSend(Message message);

    /**
     * 发送可靠性消息
     */
    void reliantSend(Message message);

    /**
     * 批量发送消息
     */
    void sendMessages();

}
