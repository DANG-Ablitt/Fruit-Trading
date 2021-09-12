package platform.queue;

import com.google.common.collect.Lists;
import platform.dto.Message;

import java.util.List;

/**
 *  使用threadLocal暂存线程中传输的消息列表
 */
public class MessageHolder {

    private List<Message> messages = Lists.newArrayList();

    private static final ThreadLocal<MessageHolder> HOLDER = ThreadLocal.withInitial(MessageHolder::new);

    public static void add(Message message) {
        HOLDER.get().messages.add(message);
    }

    public static List<Message> getAndClear() {
        List<Message> tmp = Lists.newArrayList(HOLDER.get().messages);
        HOLDER.remove();
        return tmp;
    }

}
