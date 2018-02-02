package com.yinqiao.af.utils;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yinqiao.af.weixin.msg.BaseResponseMessage;
import com.yinqiao.af.weixin.msg.BaseTemplateMessage;

//微信消息推送工具类
public class PushMsgUtil {
	private static Logger log = LoggerFactory.getLogger(PushMsgUtil.class);
	
	private static final String PUSH_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

	private static final String PUSH_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	
	private static final String AUTH2_GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code"; 
	
	public static int push(BaseResponseMessage message){
		String content = message.toJSONString();
		return push(content,PUSH_MSG_URL);
		
	}
	
	//客服消息推送(48小时内与公众号有交互)
	public static int push(String content){
		return push(content,PUSH_MSG_URL);
	}
	
	//模板消息推送(微信提供消息模板套用)
	public static int push(BaseTemplateMessage message){
		String content = message.toJSONString();
		return push(content,PUSH_TEMPLATE_URL);
	}
	
	private static int push(String message,String url){
		int result =0;
		//启动定时任务后，从数据库取ACCESS_TOKEN，不用每次都要向微信公众平台请求
		String accessToken = AccessUtil.getaAccessToken(Configuration.APPID, Configuration.SECRET);	
		String forkURL = url.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = HttpsRequestUtil.httpsRequest(forkURL, HttpsRequestUtil.POST, message);
		log.info("The message push to Wechat Server is:{}.And the URL is:{}",message,forkURL);
		if(message != null){
			if(jsonObject.getInt("errcode")!=0){
				result = jsonObject.getInt("errcode");
				 log.error("Message Sending errorr! errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return result;
	}
	
	/**
	 * 回调地址获取openid、accesstoken
	 * @param code
	 * @return
	 */
	public static JSONObject getAuthAccessToken(String code){
		return getAuthAccessToken(code,AUTH2_GET_ACCESS_TOKEN_URL);
	}
	
	public static JSONObject getAuthAccessToken(String code, String url){
		String forkURL = url.replace("APPID", Configuration.APPID)
				.replace("SECRET", Configuration.SECRET)
				.replace("CODE", code);
		log.info("The code push to Get Access Token is:{}.And the URL is:{}",code,forkURL);
		JSONObject jsonObject = HttpsRequestUtil.httpsRequest(forkURL, HttpsRequestUtil.GET, "");
		return jsonObject ;
	}
}
