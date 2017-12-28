package com.yinqiao.af.utils;

public class Configuration{
	
	public static String APPID  = "wx8feec63e9ca4bfbb";
	
	//ws通过code换取用户session信息
	public static String WX_GETOPENID_URL = "https://api.weixin.qq.com/sns/jscode2session";
	
	//ws支付统一下单接口地址
	public static String PAY_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	public static String SECRET;
	
	public static String MCH_ID = "1220667501";
	
	public static String TOKEN;
	
	public static String LOCALE;
	
	public static String BOSS_ADDR_MAIN;
	
	public static String BOSS_PORT;
	
	public static String LOCATION_MSG_ID;
	
	public static String ACCESS_TK;
	
	public static String JSAPI_TICKET;
		
	 public  Configuration(){
		
	}
	 
}
