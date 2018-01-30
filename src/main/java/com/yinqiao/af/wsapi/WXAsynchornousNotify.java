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

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.yinqiao.af.model.OrderInfo;
import com.yinqiao.af.model.User;
import com.yinqiao.af.service.IEnrollService;
import com.yinqiao.af.service.IOrderInfoService;
import com.yinqiao.af.service.IUserService;
import com.yinqiao.af.service.impl.UserServiceImpl;


@Controller
@RequestMapping(value = "/wxPay")
public class WXAsynchornousNotify {

	private Logger log = LoggerFactory.getLogger(WXAsynchornousNotify.class);

	@Autowired
	private IOrderInfoService orderInfoService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IEnrollService enrollService;
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
			String orderId =  out_trade_no.substring(0, out_trade_no.length() - 3);//原始订单id
			log.info("接收信息状态："+ return_code  + "订单号："+ orderId + "交易流水号："+ transaction_id);

			if("SUCCESS".equals(return_code)){
				OrderInfo orderInfo = orderInfoService.selectByPrimaryKey(orderId);//原始订单信息
				if(orderInfo != null){
					result = this.setXml("SUCCESS", "OK");
					orderInfo.setStatus("1");
					orderInfo.setTradeNo(transaction_id);
					orderInfo.setPaytime(new Date());
					orderInfo.setAmount(total_fee);
					orderInfo.setOrderId(orderId);
					orderInfoService.updateByPrimaryKey(orderInfo);//支付信息更新
					User user = userService.selectByPrimaryKey(orderInfo.getTelnum());
					if (user != null) {
						if ("M01".equals(orderInfo.getProductId())) {
							if ("1".equals(enrollService.queryIsEnrolled(user.getIdcard()))) {
								user.setUserstatus("3");
							}else {
								user.setUserstatus("2");
							}
							user.setEffstart(new Date());
							user.setEffend(this.getMonthDate(1));
						}else if("Y01".equals(orderInfo.getProductId())){
							user.setUserstatus("4");
							user.setEffstart(new Date());
							user.setEffend(this.getMonthDate(36));
						}
						userService.updateByPrimaryKey(user);
					}
				}else{
					result = this.setXml("fail", "订单不存在");
				}
			}else{
				result =  this.setXml("fail", "交易失败");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		log.info("返回信息:"+result);
		return result; 
	}

	private Date getMonthDate(int mouth){
		Date date=new   Date();//取时间
		System.out.println(date.toString());
	    Calendar calendar = new GregorianCalendar(); 
	    calendar.setTime(date); 
	    calendar.add(calendar.MONTH, mouth);//把日期往后增加一个月.整数往后推,负数往前移动
	    date=calendar.getTime();   //这个时间就是日期往后推一天的结果 
	    return date;
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
		Date date=new   Date();//取时间
		System.out.println(date.toString());
	    Calendar   calendar   =   new   GregorianCalendar(); 
	    calendar.setTime(date); 
//	    calendar.add(calendar.YEAR, 1);//把日期往后增加一年.整数往后推,负数往前移动
	    calendar.add(calendar.MONTH, 36);//把日期往后增加一个月.整数往后推,负数往前移动
//	    calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
//	    calendar.add(calendar.WEEK_OF_MONTH, 1);//把日期往后增加一个月.整数往后推,负数往前移动
	    date=calendar.getTime();   //这个时间就是日期往后推一天的结果 
	    System.out.println(date);
	}
}
