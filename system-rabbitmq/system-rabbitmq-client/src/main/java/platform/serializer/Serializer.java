package platform.serializer;

/**
 * 序列化和反序列化的接口
 */
public interface Serializer {

    /**
     * 对象序列化成字节
     */
    byte[] serializeRaw(Object data);

    /**
     * 序列化对象成String
     */
    String serialize(Object data);

    /**
     * 反序列化
     */
    <T> T deserialize(String content);

    /**
     * 字节反序列化成对象
     */
    <T> T deserialize(byte[] content);

}
