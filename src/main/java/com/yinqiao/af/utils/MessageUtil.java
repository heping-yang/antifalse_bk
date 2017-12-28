package com.yinqiao.af.utils;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;


public class MessageUtil {
	
	public static final String RESP_MESSAGE_TYPE_TCS = "transfer_customer_service";
	
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";
	
	public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
	
	public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
	
	public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
	
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
	
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";
	
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";
	
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
	
	public static final String REQ_MESSAGE_TYPE_LINK = "link";
	
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
	
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
	
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";
	
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
	
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	
	public static final String EVENT_TYPE_SCAN = "SCAN";
	
	public static final String EVENT_TYPE_LOCATION = "LOCATION";
	
	public static final String EVENT_TYPE_CLICK = "CLICK";
	
	public static final String EVENT_TYPE_VIEW = "VIEW";
	public static final String EVENT_TYPE_USER_GET_CARD = "user_get_card";
	public static final String EVENT_TYPE_TEMPLATESENDJOBFINISH = "TEMPLATESENDJOBFINISH";
	public static final String EVENT_TYPE_MASSSENDJOBFINISH = "MASSSENDJOBFINISH";
	public static final String EVENT_TYPE_CARD_PASS_CHECK = "card_pass_check";
	public static final String EVENT_TYPE_USER_DEL_CARD = "user_del_card";
	public static final String EVENT_TYPE_USER_CONSUME_CARD = "user_consume_card";
	public static final String EVENT_TYPE_USER_VIEW_CARD = "user_view_card";
	public static final String EVENT_TYPE_USER_ENTER_SESSION_FROM_CARD = "user_enter_session_from_card";
	
	private static Logger log = LoggerFactory.getLogger(MessageUtil.class);
	
	@SuppressWarnings("unchecked")
	public static Map<String , String> parseXml(HttpServletRequest request) throws Exception{
		Map<String, String > map = new HashMap<String, String>();
		InputStream inputStream = request.getInputStream();
		SAXReader reader = new SAXReader();
		
		Document document = reader.read(inputStream);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		
		for(Element element: elementList){
			map.put(element.getName(), element.getText());
		}
		
		inputStream.close();
		inputStream = null;
		return map;
		
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String , String> parseXml(String xmlInfo) throws Exception{
		Map<String, String > map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		Document document = reader.read(new ByteInputStream(xmlInfo.getBytes(), xmlInfo.getBytes().length +20));
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		
		for(Element element: elementList){
			map.put(element.getName(), element.getText());
		}
		return map;	
	}
	
	@SuppressWarnings("unchecked")
	public static String parseMap(Map<String,String> paramMap){
		//log.info("parseMap start");
		try{
			SAXReader reader = new SAXReader();
			Document document = DocumentHelper.createDocument();  ;
			Element rootElement = document.addElement("xml");  
			Element element = null;
			Iterator<String> itor = paramMap.keySet().iterator();
			String elementName =  null;
			while (itor.hasNext()){
				elementName = itor.next();
				element = rootElement.addElement(elementName) ;
				//element.addCDATA(paramMap.get(elementName));
				element.addText(paramMap.get(elementName) == null?"":paramMap.get(elementName));
			}
			//log.info("document.asXML(): "+document.asXML());
			return document.asXML();	

		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
