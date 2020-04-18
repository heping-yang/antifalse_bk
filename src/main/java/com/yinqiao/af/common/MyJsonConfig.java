package com.yinqiao.af.common;

import java.util.Date;

import net.sf.json.JsonConfig;

public class MyJsonConfig {
	public static JsonConfig instance(){
		return instance("yyyy-MM-dd HH:mm:ss");
	}
	public static JsonConfig instance(String format) {
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(format));
		return config;
	}
}
