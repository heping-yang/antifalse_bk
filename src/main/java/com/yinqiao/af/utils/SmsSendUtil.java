package com.yinqiao.af.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * 短信群发服务
 * 
 */
public class SmsSendUtil {
	/** 用户名常量 */
	public static final String UID = "nxyqedu";
	/** 用户密码常量 */
	public static final String PWD = "nxyqedu5034710";
	
	
	/** 群发短信记录管理 */

	/**
	 * 完全构造函数：进行密码的MD5加密，内容的URL编码
	 * 
	 * @param mobile
	 *            手机号码字符串（用","隔开）
	 * @param content
	 *            短信内容(未指定长度，默认长度参数为0)
	 * @param senderId
	 * &uid=test&pwd=b9887c5ebb23ebb294acab183ecf0769&mobile=13900008888&content=你好！testuser,您的验证码：234346。如非本人操作，可不用理会！【公司签名】
	 */
	public static String SendSms(String mobile, String smscode) {
		StringBuffer sb = new StringBuffer();
		sb.append("ac=send");
		sb.append("&uid="+UID);
		sb.append("&pwd="+JSDKUtil.encodeByMD5(PWD+UID));
		sb.append("&mobile="+mobile);
		String content = "";
		try {
			content = URLEncoder.encode("{\"code\":\""+smscode+"\",\"product\":\"反假货币在线考试平台\"}","UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sb.append("&template=417511");
		sb.append("&content="+content);
		sb.append("&encode=utf8");
		return HttpRequest.sendGet(Configuration.SMS_URL, sb.toString());
	}
}
