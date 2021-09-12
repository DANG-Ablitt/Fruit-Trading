package platform.convert;

import com.google.common.base.Preconditions;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 *  装饰 序列化转换器：在properties中
 */
public class RabbitMessageConverter implements MessageConverter {

    private GenericMessageConverter delegate;

    public RabbitMessageConverter(GenericMessageConverter genericMessageConverter) {
        //Preconditions.checkNotNull(genericMessageConverter);
        delegate = genericMessageConverter;
    }

    /**
     * cn.xuyk.rabbit.api.pojo.Message -> org.springframework.amqp.core.Message
     */
    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        platform.dto.Message message = (platform.dto.Message)object;
        messageProperties.setDelay(message.getDelayMills());
        return delegate.toMessage(object, messageProperties);
    }

    /**
     * org.springframework.amqp.core.Message -> cn.xuyk.rabbit.api.pojo.Message
     */
    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        platform.dto.Message msg = (platform.dto.Message) delegate.fromMessage(message);
        return msg;
    }

}
