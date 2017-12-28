package com.yinqiao.af.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoTask {
	
	private static Logger logger = LoggerFactory.getLogger(DemoTask.class);
	
	/*@Resource(name="redisTemplate")
	private ValueOperations<String, Object> valOps;*/
	
	public void execute(){
		logger.info("任务开始执行。。。");
		/*valOps.set("lastTime", new Date(), 60, TimeUnit.MINUTES);//5分钟有效
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info(sdf.format((Date)valOps.get("lastTime")));*/
	}
}
