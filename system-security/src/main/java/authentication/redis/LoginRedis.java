package authentication.redis;

import org.springframework.stereotype.Component;

@Component
public class LoginRedis {

    /**
     * 保存对象参数
     */
    public void set(Object object){
        if(object==null){
            return;
        }

    }

    /**
     * 读取对象参数
     */
    public Object get(String uuid){
        return null;

    }
}
