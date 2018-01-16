package com.yinqiao.af.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

/**
 * 微信支付工具类
 * @author kai
 *
 */
public class WeixinPaymentUtil{

	private static Logger log = LoggerFactory.getLogger(WeixinPaymentUtil.class);
	
	//支付秘钥
	private static String key = "2Hfkw5jLdHTTSxodcWR3eol4BRuRx0GE";

	/**
	 * 拼接签名参数
	 * @param packageMy
	 * @return
	 */
	public static Map<String,String> getSignString(String packageMy){
		Map<String,String> retMap = new HashMap<String,String>();
		String noncestr = JSDKUtil.getNoncestr();
		long timestamp =JSDKUtil.getTimestamp();
		log.info("Get Sign String by noncestr:{}, timestamp{}, packageMy{}, APPID:{}",noncestr,timestamp,packageMy,Configuration.APPID);
		retMap.put("appId", Configuration.APPID);
		retMap.put("timeStamp", timestamp + "");
		retMap.put("nonceStr", noncestr);
		retMap.put("package", "prepay_id="+packageMy);
		retMap.put("signType", "MD5");
		log.info("Get Pay Sign Use SHAR1 set to:{}",JSDKUtil.encodeByMD5(createLinkString(retMap)+"&key="+key));
		retMap.put("paySign",JSDKUtil.encodeByMD5(createLinkString(retMap)+"&key="+key).toUpperCase());
		return retMap ;
	}

	/**
	 * 签名参数
	 * @param paramMap
	 * @return
	 */
	public static Map<String, String> paymentSign(Map<String,String> paramMap ){
		String nonce_str =  JSDKUtil.getNoncestr();
		paramMap.put("nonce_str", nonce_str);
		log.info("Generate nonce_str set to :{}.",nonce_str);
		String signStr = createLinkString(paramMap);
		log.info("Generate signStr set to :{}.",signStr+"&key="+key);
		signStr += "&key="+key;
		String signedStr = JSDKUtil.encodeByMD5(signStr);
		log.info("Generate signedStr set to :{}.",signedStr);
		paramMap.put("sign", signedStr.toUpperCase());
		return paramMap;
	}
	
	/**
	 * 参数拼接
	 * 
	 */
	public static String createLinkString(Map<String, String> params) {
		if ((params == null) || (params.size() == 0)) {
			return "";
		}

		TreeMap treeMap = new TreeMap(params);

		StringBuilder buffer = new StringBuilder(128);

		Set<Entry<String, String>> set = treeMap.entrySet();
		for (Iterator<Entry<String, String>> it = set.iterator(); it.hasNext();) {
			Entry<String, String> entry = it.next();
			String value = (String) entry.getValue();
			if ((value == null) || ("".equals(value))) {
				continue;
			}
			buffer.append((String) entry.getKey()).append('=').append(value).append('&');
		}
		
		String returnStr = buffer.substring(0,buffer.length() > 0 ? buffer.length() - 1 : 0);

		return returnStr;
	}
	
	/**
	 * xml解析
	 * @param xmlInfo
	 * @param encode
	 * @return
	 * @throws Exception
	 */
	public static Map<String , String> parseXml(String xmlInfo,String encode) throws Exception{
		Map<String, String > map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		Document document = reader.read(new ByteInputStream(xmlInfo.getBytes(encode), xmlInfo.getBytes().length +20));
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();

		for(Element element: elementList){
			map.put(element.getName(), element.getText());
		}
		return map;	
	}

	/**
	 * 验证报文签名
	 * @param xmlInfo
	 * @return
	 */
	public static boolean checkSign(String xmlInfo){
		boolean flag = false;
		try {
			Map<String, String> paramMap = parseXml(xmlInfo,"UTF-8");
			if (paramMap != null && paramMap.size() > 0){
				String sign = paramMap.get("sign");
				paramMap.remove("sign");
				String localSign = signMap(paramMap);
				if (localSign != null &&
						localSign.equals(sign)){
					flag = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 对指定map签名
	 * @param paramMap
	 * @return
	 */
	public static String signMap(Map<String,String> paramMap ){
		String signStr =  WeixinPaymentUtil.createLinkString(paramMap);
		String signedStr = JSDKUtil.encodeByMD5(signStr+"&key="+key);
		return signedStr.toUpperCase();
	}

	public static String map2String(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sb.append(entry.getKey() + "=" + entry.getValue() + ";");
		}
		return sb.toString();
	}
	

	/**
	 * 获取客户端IP
	 * 
	 * @param request
	 * @return
	 * @remark create cKF49884 2012/10/12 R003C12L09n01 OR_SD_201208_1121
	 */
	public static String getClientIp(HttpServletRequest request)
	{
		// 防钓鱼客户端ip获取
		String clientIp = request.getHeader("x-forwarded-for");
		if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp))
		{
			clientIp = request.getHeader("Proxy-Client-IP");
		}
		if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp))
		{
			clientIp = request.getHeader("WL-Proxy-Client-IP");
		}
		if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp))
		{
			clientIp = request.getRemoteAddr();
		}
		//防止出现多个ip地址
		if(null != clientIp && clientIp.indexOf(",")>0){
			clientIp = clientIp.split(",")[0];
		}
		return clientIp;
	}
	
	public static void main(String[] args) {
		System.out.println(JSDKUtil.encodeByMD5("appid=wxfbcd9d55b2599597&body=365购买&detail=365&mch_id=1496240462&nonce_str=YNfYJjyoCzCtjIC&notify_url=https://www.nxyqedu.com/antifalse/wxPay/asynchronousNotify&openid=oUyEW0d8q0VJqZYHKpipWsBNa1Ec&out_trade_no=3f30424e645e9891a3acc6b008e8b579&product_id=pro01&spbill_create_ip=10.223.24.18&total_fee=1&trade_type=JSAPI&key=2Hfkw5jLdHTTSxodcWR3eol4BRuRx0GE"));
	}
}
