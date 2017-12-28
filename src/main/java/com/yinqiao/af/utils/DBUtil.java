package com.yinqiao.af.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DBUtil {
	private static ApplicationContext context = null;
	
	private DBUtil(){
		String resource = "applicationContext.xml";
		context = new ClassPathXmlApplicationContext(resource);
	}
	
	public ApplicationContext getContext(){
		return context;
	}
	
	public static ApplicationContext getInstance(){
		if(context == null){
			return new DBUtil().getContext();			
		}
		return context;
	}
}
