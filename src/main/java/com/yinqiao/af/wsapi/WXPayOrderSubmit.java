package com.yinqiao.af.wsapi;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinqiao.af.business.BaseAction;
import com.yinqiao.af.model.OrderInfo;
import com.yinqiao.af.service.IOrderInfoService;
import com.yinqiao.af.utils.Configuration;
import com.yinqiao.af.utils.HttpsRequestUtil;
import com.yinqiao.af.utils.MessageUtil;
import com.yinqiao.af.utils.WeixinPaymentUtil;

/**
 * 活动支付订单提交接口 ordersubmitopen
 */

@Service("wsordersubmitApi")
public class WXPayOrderSubmit extends BaseAction{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IOrderInfoService orderInfoService;

	/**
	 * 支付信息提交并返回支付参数
	 * @param request
	 * @param response
	 * @param param
	 * @param model
	 */
	public String payOrder(HttpServletRequest request, HttpServletResponse response) {
		JSONObject req = new JSONObject();
		try {
			System.out.println("ordersubmitApi");
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setAmount(request.getParameter("prodFee"));
			orderInfo.setProductId(request.getParameter("productId"));
			orderInfo.setProductName(request.getParameter("productName"));
			orderInfo.setOrderId(request.getParameter("orderId"));
			orderInfo.setTelnum(request.getParameter("phone"));
			orderInfo.setDescription(request.getParameter("proddesc"));
			orderInfo.setOpenid(request.getParameter("openid"));
			orderInfo.setStatus("0");
			orderInfo.setCreateTime(new Date());
			//查询 已有支付信息 做更新 否则增加
			orderInfoService.insert(orderInfo);
			req.put("body", JSONObject.fromObject(getJSpay(orderInfo)).toString());
			logger.info("=========支付订单=========="+orderInfo.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("返回"+req.toString());
		return req.toString();
	}

	/**
	 * 微信统一下单JSAPI
	 * @param servNumber
	 * @param openid
	 * @param reqbody
	 * @param orderInfo
	 * @return
	 * @throws IOException
	 */
	public Map<String,String> getJSpay(OrderInfo orderInfo) throws IOException{		
		logger.info("@@@@@ enter jsapi pay :{}");
		try{
			String returnXml = HttpsRequestUtil.httpsRequestInner(Configuration.PAY_ORDER_URL, HttpsRequestUtil.POST,getOrderInfoMap(orderInfo));
			logger.info("The Xml info fetched from wechat server is :{}.",returnXml);
			if (!WeixinPaymentUtil.checkSign(returnXml)){
				Map<String,String> returnMap = WeixinPaymentUtil.parseXml(returnXml,"UTF-8");
				logger.info("The Xml info fetched from wechat check sign failed.");
				return returnMap;
			}
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
					String prepay_id = returnMap.get("prepay_id");
					logger.info("Get prepay_id from wechat server is :{}.",prepay_id);
					paramMap =  WeixinPaymentUtil.getSignString(prepay_id);
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
	
	/**
	 * 微信获取Openid
	 */
	public String getOpenid(HttpServletRequest request, HttpServletResponse response) throws IOException{
		JSONObject req = new JSONObject();
		String url = Configuration.WX_GETOPENID_URL;
		url = url.replaceAll("APPID", Configuration.APPID);
		url = url.replaceAll("SECRET", Configuration.SECRET);
		url += "&js_code="+ request.getParameter("code");
		System.out.println(url);
		try{
			String returnXml = HttpsRequestUtil.httpsRequestInner(url, HttpsRequestUtil.GET,"");
			logger.info(returnXml);
			System.out.println(returnXml.substring(returnXml.indexOf("openid")+9, returnXml.length()-2));
			req.put("openid", returnXml.substring(returnXml.indexOf("openid")+9, returnXml.length()-2));
			System.out.println(req.toString());
		}catch(JSONException e){
			logger.error(e.getMessage());
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return req.toString();
	}

	/**
	 * 预下订单接口生成签名、生成参数map
	 * @param orderInfo 
	 * @param openid 
	 *  
	 * @param request
	 * @param response 	
	 * @param busiType 业务类型
	 * @param syslog 日志
	 * @throws Exception 
	 */
	public String getOrderInfoMap(OrderInfo orderInfo){
		Map<String, String> retparamMap = new HashMap<String,String>();		
		String out_trade_no = orderInfo.getOrderId()+(int)((Math.random()*9+1)*100); //随机生成新的订单号，防止重复订单 
		System.out.println(out_trade_no);
		try{
			logger.info("getOrderInfoMap orderId()"+ orderInfo.getOrderId());
			retparamMap.put("appid", Configuration.APPID);
			retparamMap.put("body", orderInfo.getProductName()+"购买");
			retparamMap.put("detail", orderInfo.getDescription());
			retparamMap.put("out_trade_no", out_trade_no);			
			retparamMap.put("total_fee", orderInfo.getAmount()+"");
			retparamMap.put("spbill_create_ip", InetAddress.getLocalHost().getHostAddress().toString());
			retparamMap.put("openid", orderInfo.getOpenid());  
			retparamMap.put("notify_url", "https://www.nxyqedu.com/antifalse/wxPay/asynchronousNotify");//微信异步通知地址
			retparamMap.put("trade_type","JSAPI");
			retparamMap.put("product_id", orderInfo.getProductId());
			retparamMap.put("mch_id", Configuration.MCH_ID); //商户id
			retparamMap = WeixinPaymentUtil.paymentSign(retparamMap);
		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			System.out.println(MessageUtil.parseMap(retparamMap));
			return MessageUtil.parseMap(retparamMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	public static void main(String[] args) {
	}
}
