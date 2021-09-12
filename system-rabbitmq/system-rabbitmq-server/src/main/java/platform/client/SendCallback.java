package platform.client;

/**
 *  消息回调函数处理
 */
public interface SendCallback {

    /**
     * 发送成功回调
     */
    void onSuccess();

    /**
     * 发送失败回调
     */
    void onFailure();

}
