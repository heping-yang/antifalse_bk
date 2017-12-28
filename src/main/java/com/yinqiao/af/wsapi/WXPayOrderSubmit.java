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
import org.springframework.ui.ModelMap;

import com.yinqiao.af.business.BaseAction;
import com.yinqiao.af.model.PayDetail;
import com.yinqiao.af.model.Retinfo;
import com.yinqiao.af.service.IPayDetailService;
import com.yinqiao.af.utils.Configuration;
import com.yinqiao.af.utils.HttpsRequestUtil;
import com.yinqiao.af.utils.MessageUtil;
import com.yinqiao.af.utils.WeixinPaymentUtil;

/**
 * 活动支付订单提交接口 ordersubmitopen
 */

@SuppressWarnings("restriction")
@Service("wsordersubmitApi")
public class WXPayOrderSubmit extends BaseAction{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IPayDetailService payDetailService;

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
			
			String openid = reqbody.getString("openid");

			PayDetail orderInfo = new PayDetail();
			orderInfo.setAmount(Integer.parseInt(reqbody.getString("PRODFEE")));
			orderInfo.setOrderType(reqbody.getString("PRODTYPE"));
			orderInfo.setOrderid(reqbody.getString("ORDERID"));
			orderInfo.setPhone("");
			orderInfo.setDescription(reqbody.getString("PRODDESC"));
			orderInfo.setStatus("0");
			orderInfo.setCreateTime(new Date());
			orderInfo.setUpdateTime(new Date());
			//查询 已有支付信息 做更新 否则增加
			Map<String,Object> payMap = payDetailService.queryPayDetailById(orderInfo.getOrderid());
			if(null != payMap && payMap.size()>0){
				payDetailService.update(orderInfo);
			}else{				
				payDetailService.insert(orderInfo);
			}
			model.put("body", JSONObject.fromObject(getJSpay(openid,reqbody,orderInfo)).toString());
			logger.info("=========支付订单=========="+orderInfo.toString());
			
			model.put("retinfo", retinfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public Map<String,String> getJSpay(String openid, JSONObject reqbody, PayDetail orderInfo) throws IOException{		
		logger.info("@@@@@ enter jsapi pay :{}");
		try{
			String returnXml = HttpsRequestUtil.httpsRequestInner(Configuration.PAY_ORDER_URL, HttpsRequestUtil.POST,getOrderInfoMap(reqbody,orderInfo,openid));
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
	public String getOrderInfoMap(JSONObject reqbody, PayDetail orderInfo, String openid){
		Map<String, String> retparamMap = new HashMap<String,String>();		
		String out_trade_no = orderInfo.getOrderid()+(int)((Math.random()*9+1)*100); //随机生成新的订单号，防止重复订单 
		try{
			logger.info("getOrderInfoMap orderId()"+ orderInfo.getOrderid());
			retparamMap.put("appid", Configuration.APPID);  		 
			retparamMap.put("attach", "");
			retparamMap.put("body", reqbody.getString("PRODNAME")+"购买");
			retparamMap.put("detail", reqbody.getString("PRODDESC"));
			retparamMap.put("out_trade_no", out_trade_no);			
			retparamMap.put("total_fee", orderInfo.getAmount()+"");
			retparamMap.put("spbill_create_ip", InetAddress.getLocalHost().getHostAddress().toString());
			retparamMap.put("openid", openid);    
			retparamMap.put("notify_url", "http://www.nx.10086.cn/ActPortal/wxPay/asynchronousNotify");//微信异步通知地址
			retparamMap.put("trade_type","JSAPI"); 
			retparamMap.put("product_id", reqbody.getString("PRODID")); 
			retparamMap.put("mch_id", Configuration.MCH_ID); //商户id
			retparamMap = WeixinPaymentUtil.paymentSign(retparamMap);			
		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			return MessageUtil.parseMap(retparamMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
}
