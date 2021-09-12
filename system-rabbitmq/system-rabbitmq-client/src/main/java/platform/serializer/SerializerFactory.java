package platform.serializer;

/**
 *  序列化工厂
 */
public interface SerializerFactory {

    /**
     * 创建序列化器
     */
    Serializer create();

}
