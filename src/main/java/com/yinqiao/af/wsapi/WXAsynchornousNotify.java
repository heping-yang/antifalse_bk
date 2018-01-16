package com.yinqiao.af.wsapi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.alibaba.dubbo.common.utils.StringUtils;
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
			System.out.println("+++++++++++++++++++++++++++++++++++++########################1");			

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = null;			
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(request.getInputStream());
			log.info("get req inputStream is =" + this.getXmlString(doc));

			Map<String,String> wxmap = AnalyWXXML2Map(doc);
			String return_code = wxmap.get("return_code");//返回结果
			String out_trade_no = wxmap.get("out_trade_no");//随机订单号
			String transaction_id = wxmap.get("transaction_id");//交易流水号
			String total_fee = wxmap.get("total_fee");//交易金额
			log.info("异步返回信息："+ return_code  + "订单号："+ out_trade_no + "交易流水号："+ transaction_id);
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
		return result; 
	}


	//通过xml 发给微信消息  
	public String setXml(String return_code, String return_msg) {  
		SortedMap<String, String> parameters = new TreeMap<String, String>();  
		parameters.put("return_code", return_code);  
		parameters.put("return_msg", return_msg);  
		return "<xml><return_code><![CDATA[" + return_code + "]]>" +   
		"</return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";  
	}  

	public String getXmlString(Document doc){  
		TransformerFactory tf = TransformerFactory.newInstance();  
		try {  
			Transformer t = tf.newTransformer();  
			t.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
			t.setOutputProperty(OutputKeys.METHOD, "html");    
			t.setOutputProperty(OutputKeys.VERSION, "4.0");    
			t.setOutputProperty(OutputKeys.INDENT, "no");    
			ByteArrayOutputStream bos = new ByteArrayOutputStream();  
			t.transform(new DOMSource(doc), new StreamResult(bos));  
			return bos.toString();  
		} catch (TransformerConfigurationException e) {  
			e.printStackTrace();  
		} catch (TransformerException e) {  
			e.printStackTrace();  
		}  
		return "";  
	}

	public static void responseContent(HttpServletResponse response,String content){  
		try {  
			byte[] xmlData = content.getBytes();     

			response.setContentLength(xmlData.length);     

			ServletOutputStream os = response.getOutputStream();  
			os.write(xmlData);     

			os.flush();     
			os.close();    
		} catch (IOException e) {  
			e.printStackTrace();  
		}     
	}  
	/**
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws 
	 * @auto thero
	 */
	public Map<String,String> AnalyWXXML2Map(Document doc) throws SAXException, IOException, ParserConfigurationException{
		String oper_code = getValueByTagName(doc, "oper_code");
		log.info(oper_code);
		String orderId = getValueByTagName(doc, "orderId");
		System.out.println(orderId);
		String tagName = "appid;bank_type;cash_fee;fee_type;is_subscribe;mch_id;nonce_str;openid;out_trade_no;result_code;return_code;" +
				"sign;time_end;total_fee;trade_type;transaction_id";
		Map<String,String> WXXML2Map = new HashMap<String,String>();
		String[] names = tagName.split(";");
		for(int i = 0 ; i < names.length;i++){
			String name = names[i];
			WXXML2Map.put(name, getValueByTagName(doc, name));
		}
		Iterator<Map.Entry<String, String>> it = WXXML2Map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			log.info( entry.getKey() + " = " + entry.getValue());
		}
		return WXXML2Map;
	}

	public static String getValueByTagName(Document doc, String tagName){
		if(doc == null || StringUtils.isEmpty(tagName)){
			return "";
		}
		NodeList pl = doc.getElementsByTagName(tagName);
		if(pl != null && pl.getLength() > 0){
			return pl.item(0).getNodeValue();
		}
		return "";
	}


}
