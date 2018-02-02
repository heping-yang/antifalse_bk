package com.yinqiao.af.utils;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//微信安全工具类
public class AccessUtil {
	
	private static Logger log = LoggerFactory.getLogger(AccessUtil.class);
	
	private static final String ACCESS_TOKEN_URL = 
			"https://api.weixin.qq.com/cgi-bin/token?" +
			"grant_type=client_credential" +
			"&appid=APPID" +
			"&secret=SECRET";
	
	public static String getaAccessToken(String appid,String secret){
		String accessToken = null;
		String requestUrl = ACCESS_TOKEN_URL
				.replace("APPID", appid)
				.replace("SECRET", secret);
		JSONObject jsonObject = HttpsRequestUtil.httpsRequest(requestUrl, HttpsRequestUtil.GET, null);
		log.info("Get access_token from URL is:{} , appid is {}, secret is {}",requestUrl,Configuration.APPID,Configuration.SECRET);
		if(jsonObject == null){
			log.info("jsonObject is null");
		}
		if(jsonObject != null){
			try{
			accessToken = jsonObject.getString("access_token");
			}catch(JSONException e){
				accessToken = null;
				log.error("��ȡtokenʧ�� errcode:{} errmsg:{}", 
						jsonObject.getInt("errcode"), 
						jsonObject.getString("errmsg"));
			}			
		}
		
		return accessToken;
	}
}
