package com.yinqiao.af.wsapi;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;

import com.yinqiao.af.model.Retinfo;
import com.yinqiao.af.utils.Configuration;
import com.yinqiao.af.utils.HttpsRequestUtil;
import com.yinqiao.af.utils.MessageUtil;
import com.yinqiao.af.utils.WeixinPaymentUtil;


public class WXAppGetUserInfo {
	 
	private static Logger logger = LoggerFactory.getLogger(WXAppGetUserInfo.class);
    
	/**
	 * 支付信息提交并返回支付参数
	 * @param request
	 * @param response
	 * @param param
	 * @param model
	 */
	public void receive(HttpServletRequest request, HttpServletResponse response, JSONObject param, ModelMap model) {
		try {
			System.out.println("ordersubmitApi");
			JSONObject reqbody = param.getJSONObject("reqbody");

			Retinfo retinfo = new Retinfo();
			retinfo.setRetCode("100");
			retinfo.setRetMsg("Success");
			
			String code = "";
			if (reqbody.containsKey("code")) {
				code = reqbody.getString("code");
				logger.info("用户code:"+code);
			}
			if (!"".equals(code)) {
				//通过code换取微信session
				model.put("body", JSONObject.fromObject(getOpenid(code)).toString());
			}else {
				model.put("body", "{return_code:'FAILED',return_msg:'code不能为空'}");
				model.put("body", JSONObject.fromObject(getOpenid(code)).toString());
			}
			model.put("retinfo", retinfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    /** 
     * 获取微信小程序 session_key 和 openid 
     *  
     * @author Lee
     * @param code 调用微信登陆返回的Code,前台js获取code
     * @return 
     */  
    public Map<String, String> getOpenid(String code){
    	//微信端登录code值  
        String wxCode = code;  
        Map<String,String> requestUrlParam = new HashMap<String,String>();  
        requestUrlParam.put("appid", Configuration.APPID);  //开发者设置中的appId
        requestUrlParam.put("secret", Configuration.SECRET); //开发者设置中的appSecret
        requestUrlParam.put("js_code", wxCode); //小程序调用wx.login返回的code
        requestUrlParam.put("grant_type", "authorization_code");//默认参数 
        //发送post请求读取调用微信 https://api.weixin.qq.com/sns/jscode2session 接口获取openid用户唯一标识  
        logger.info("@@@@@ enter getOpenid :{}");
		try{
			String returnXml = HttpsRequestUtil.httpsRequestInner(Configuration.WX_GETOPENID_URL,HttpsRequestUtil.POST,MessageUtil.parseMap(requestUrlParam));  
			logger.info("The Xml info fetched from wechat server is :{}.",returnXml);
			
			Map<String,String> returnMap = WeixinPaymentUtil.parseXml(returnXml,"UTF-8");	
			Map<String,String> paramMap = null;
			if(returnMap == null){
				paramMap = new HashMap<String, String>();
				paramMap.put("return_code", "empty");
			}else if("FAIL".equals(returnMap.get("return_code"))){
				paramMap = new HashMap<String, String>();
				paramMap.put("return_code", returnMap.get("return_code"));
				paramMap.put("return_msg", returnMap.get("return_msg"));
			}else if("SUCCESS".equals(returnMap.get("return_code"))){
				if("SUCCESS".equals(returnMap.get("result_code"))){
					paramMap =  returnMap;
					logger.info("@@@@@ Analytical jspapi paramap  :{}" + WeixinPaymentUtil.map2String(paramMap));
					paramMap.put("return_code", "SUCCESS");
				}else{
					paramMap = new HashMap<String, String>();
					paramMap.put("return_code", returnMap.get("result_code"));
					paramMap.put("return_msg", returnMap.get("err_code_des"));
				}				
			}
			return paramMap;
		}catch(JSONException e){
			logger.error(e.getMessage());			
		}catch (Exception e) {
			logger.error(e.getMessage());			
		}		
		return null;
    }
}
