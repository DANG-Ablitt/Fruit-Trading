package platform.config;

import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import platform.dto.Message;
import platform.serializer.Serializer;
import platform.serializer.impl.JacksonSerializer;
import platform.convert.GenericMessageConverter;
import platform.convert.RabbitMessageConverter;

/**
 *  初始化序列化器bean
 */
@Configuration
public class ConvertConfig {

    @Bean
    public MessageConverter messageConverter() {
        // 消费者新增Jackson序列化器
        Serializer serializer = JacksonSerializer.createParametricType(Message.class);
        GenericMessageConverter gmc = new GenericMessageConverter(serializer);
        // 装饰者模式
        return new RabbitMessageConverter(gmc);
    }

}
