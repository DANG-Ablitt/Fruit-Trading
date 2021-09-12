package platform.serializer.impl;



import platform.dto.Message;
import platform.serializer.Serializer;
import platform.serializer.SerializerFactory;

/**
 * 序列化工厂
 */
public class JacksonSerializerFactory implements SerializerFactory {

    /**
     * 简单的单例模式
     */
    public static final SerializerFactory INSTANCE = new JacksonSerializerFactory();

    @Override
    public Serializer create() {
        return JacksonSerializer.createParametricType(Message.class);
    }

}
