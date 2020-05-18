package com.yinqiao.af;

import java.io.IOException;
import java.util.Properties;

import com.yinqiao.af.common.Global;

public class AppLoader {
	public void init(){
		Properties prop = new Properties();
		try {
			System.out.println("开始读取配置文件【antifalse.properties】");
			prop.load(AppLoader.class.getClassLoader().getResourceAsStream("antifalse.properties"));
			String exam_limit_time =  getValue(prop, "exam_limit_time", "0");
			Global.EXAM_LIMIT_TIME = Long.parseLong(exam_limit_time);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private String getValue(Properties prop,String key,String def){
		Object val =  prop.get(key);
		String str = "";
		StringBuffer sb = new StringBuffer();
		sb.append(key+"=");
		if(val!=null){
			str = val.toString();
			sb.append(str);
		}else{
			str = def;
			sb.append(str).append("[默认值]");
		}
		System.out.println(sb.toString());
		return str;
	}
}
