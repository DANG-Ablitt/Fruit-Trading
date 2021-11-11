package platform.task;

import cn.hutool.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.RedisUtils;
import utils.SpringContextUtils;

/**
 * 秒杀开始时 由定时任务将秒杀开始标志置1
 */
@Component("shopInfoRedisTask")
public class ShopInfoRedisTask implements ITask{

	@Autowired
	private RedisUtils redisUtils;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void run(String params){
		logger.debug("ShopInfoRedisTask定时任务正在执行，参数为：{}", params);
		System.out.println("ShopInfoRedisTask定时任务正在执行，参数为：{}");
		// 修改 redis 集群中的开始标志
		redisUtils.set(params+"_info",1);
	}

	public static void main(String[] args) {
		System.out.println(SpringContextUtils.getBean(ShopInfoRedisTask.class));
	}
}