package platform.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import utils.SpringContextUtils;

/**
 * 测试定时任务(演示Demo，可删除)
 * testTask为spring bean的名称
 */
@Component("testTask")
public class TestTask implements ITask{
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void run(String params){
		System.out.println("TestTask定时任务正在执行，参数为："+params);
		logger.debug("TestTask定时任务正在执行，参数为：{}", params);
	}

	public static void main(String[] args) {
		System.out.println(SpringContextUtils.getBean(TestTask.class));
	}
}