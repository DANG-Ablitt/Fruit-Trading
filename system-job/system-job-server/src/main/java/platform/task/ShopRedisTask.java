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
 * 秒杀开始前12小时 由定时任务将秒杀数据写入redis
 */
@Component("shopRedisTask")
public class ShopRedisTask implements ITask{

	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private RedisTemplate redisTemplate;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void run(String params){
		logger.debug("ShopRedisTask定时任务正在执行，参数为：{}", params);
		System.out.println("ShopRedisTask定时任务正在执行，参数为："+params);
		// JSON 参数解析
		JSONObject jsonObject = new JSONObject(params);
		// 秒杀商品 id
		Long shop_id = Long.valueOf(jsonObject.get("shop_id").toString()) ;
		// 存入 redis 集群
		redisUtils.set(Long.toString(shop_id)+"_info",0);
		// 秒杀商品库存
		Integer shop_count = Integer.valueOf(jsonObject.get("shop_count").toString());
		// 存入 redis 集群
		redisUtils.set(Long.toString(shop_id)+"_count",shop_count);
		// 用户集合
		redisTemplate.opsForSet().add(Long.toString(shop_id)+"_list","");
	}

	public static void main(String[] args) {
		System.out.println(SpringContextUtils.getBean(ShopRedisTask.class));
	}
}