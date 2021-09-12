package platform.client;

import platform.dto.Message;

import java.util.List;

public interface MessageProducer {

    /**
     * 发送消息
     */
    void send(Message message) throws Exception;

    /**
     * 批量发送消息
     */
    void send(List<Message> messages) throws Exception;

    /**
     * 发送消息 附带SendCallback回调执行响应的业务逻辑处理
     */
    void send(Message message, SendCallback sendCallback) throws Exception;

}