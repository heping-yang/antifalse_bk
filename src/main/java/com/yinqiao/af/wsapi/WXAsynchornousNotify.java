package com.yinqiao.af.wsapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.yinqiao.af.model.PayDetail;
import com.yinqiao.af.service.IPayDetailService;


@Controller
@RequestMapping(value = "/wxPay")
public class WXAsynchornousNotify {

	private Logger log = LoggerFactory.getLogger(WXAsynchornousNotify.class);

	@Autowired
	private IPayDetailService payDetailService;
	/**
	 * 微信支付异步通知接口，修改订单状态
	 * @Title: excute   
	 * @Description: TODO   
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model
	 * @param: @return
	 * @return: String      
	 * @throws
	 */

	@RequestMapping(value = "/asynchronousNotify")
	@ResponseBody
	public String excute(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		System.out.println("微信回调....");
		String result = "";
		try {
			String xmlStr = Inputstr2Str_Reader(request.getInputStream(),"UTF-8");
			log.info("get req inputStream is =" + xmlStr);
			Map<String,String> wxmap = AnalyWXXMLtoMap(xmlStr);
			String return_code = wxmap.get("return_code");//返回结果
			String out_trade_no = wxmap.get("out_trade_no");//随机订单号
			String transaction_id = wxmap.get("transaction_id");//交易流水号
			String total_fee = wxmap.get("total_fee");//交易金额
			log.info("接收信息状态："+ return_code  + "订单号："+ out_trade_no + "交易流水号："+ transaction_id);
			String orderId =  out_trade_no.substring(0, out_trade_no.length() - 3);//原始订单id
			PayDetail orderinfo = new PayDetail();			
			
			if("SUCCESS".equals(return_code)){
				Map<String, Object> orderMap = payDetailService.queryPayDetailById(orderId);//原始订单信息
				if(orderMap != null && orderMap.size()>0){
					orderinfo.setStatus("1");
					result = this.setXml("SUCCESS", "OK");
				}else{
					result = this.setXml("fail", "订单不存在");
				}
			}else{
				orderinfo.setStatus("0");
				result =  this.setXml("fail", "交易失败");  
			}
			orderinfo.setTradeNo(transaction_id);
			orderinfo.setPayTime(new Date());
			orderinfo.setAmount(Integer.parseInt(total_fee));
			orderinfo.setUpdateTime(new Date());
			orderinfo.setOrderId(orderId);
			payDetailService.update(orderinfo);//支付信息更新
		}catch (Exception e) {
			e.printStackTrace();
		}
		log.info("返回信息:"+result);
		return result; 
	}

	   public String Inputstr2Str_Reader(InputStream in, String encode)  
	   {  
	         
	       String str = "";  
	       try  
	       {  
	           if (encode == null || encode.equals(""))  
	           {  
	               // 默认以utf-8形式  
	               encode = "utf-8";  
	           }  
	           BufferedReader reader = new BufferedReader(new InputStreamReader(in, encode));  
	           StringBuffer sb = new StringBuffer();  
	             
	           while ((str = reader.readLine()) != null)  
	           {  
	               sb.append(str).append("\n");  
	           }  
	           return sb.toString();  
	       }  
	       catch (UnsupportedEncodingException e1)  
	       {  
	           e1.printStackTrace();  
	       }  
	       catch (IOException e)  
	       {  
	           e.printStackTrace();  
	       }  
	         
	       return str;  
	   }

	//通过xml 发给微信消息  
	public String setXml(String return_code, String return_msg) {  
		SortedMap<String, String> parameters = new TreeMap<String, String>();  
		parameters.put("return_code", return_code);  
		parameters.put("return_msg", return_msg);  
		return "<xml><return_code><![CDATA[" + return_code + "]]>" +   
		"</return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";  
	}
	/**
	 * @throws DocumentException 
	 * @throws UnsupportedEncodingException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws 
	 * @auto thero
	 */
	public Map<String, String> AnalyWXXMLtoMap(String xmlStr) throws UnsupportedEncodingException, DocumentException {
		SAXReader reader = new SAXReader();
		reader.setEncoding("UTF-8");
		Document document = reader.read(new ByteInputStream(xmlStr.getBytes("UTF-8"), 10000));
		document.setXMLEncoding("UTF-8");
		String tagName = "appid;bank_type;cash_fee;fee_type;is_subscribe;mch_id;nonce_str;openid;out_trade_no;result_code;return_code;" +
				"sign;time_end;total_fee;trade_type;transaction_id";
		Map<String,String> map = new HashMap<String,String>();
		String[] names = tagName.split(";");
		for(int i = 0 ; i < names.length;i++){
			String name = names[i];
			try {
				map.put(name, ((Element) document.selectNodes("xml/"+name).get(0)).getStringValue());
			} catch (Exception e) {
				log.info(name + "节点不存在");
			}
		}
		Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			System.out.println( entry.getKey() + " = " + entry.getValue());
		}
		return map;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, DocumentException {
		WXAsynchornousNotify notify = new WXAsynchornousNotify();
		String xmlStr = "	<xml><appid><![CDATA[wxfbcd9d55b2599597]]></appid> " + 
	"<bank_type><![CDATA[CFT]]></bank_type>" + 
	"<cash_fee><![CDATA[1]]></cash_fee>" + 
	"<fee_type><![CDATA[CNY]]></fee_type>" + 
	"<is_subscribe><![CDATA[N]]></is_subscribe>" + 
	"<mch_id><![CDATA[1496240462]]></mch_id>" + 
	"<nonce_str><![CDATA[ONraflzWamOVmzl]]></nonce_str>" + 
	"<openid><![CDATA[oUyEW0d8q0VJqZYHKpipWsBNa1Ec]]></openid>" + 
	"<out_trade_no><![CDATA[5d613593b48c98c454b3ae9af7e6f457]]></out_trade_no>" + 
	"<result_code><![CDATA[SUCCESS]]></result_code>" + 
	"<return_code><![CDATA[SUCCESS]]></return_code>" + 
	"<sign><![CDATA[F9FD7F634318BFF26A89489EBC4ED93B]]></sign>" + 
	"<time_end><![CDATA[20180125161945]]></time_end>" + 
	"<total_fee>1</total_fee>" + 
	"<trade_type><![CDATA[JSAPI]]></trade_type>" + 
	"<transaction_id><![CDATA[4200000067201801250564006059]]></transaction_id>" + 
	"</xml>";
		notify.AnalyWXXMLtoMap(xmlStr);
	}
}
