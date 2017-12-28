package com.yinqiao.af.business;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yinqiao.af.model.Retinfo;
import com.yinqiao.af.utils.AESCrypto;

import freemarker.template.TemplateModelException;

@Service("showIndexApi")
public class OprShopIndex extends BaseAction{
	
	private static Logger logger = LoggerFactory.getLogger(OprShopIndex.class);
	
	
	
	public void queryIndex(HttpServletRequest request, HttpServletResponse response, JSONObject param, ModelMap model)
			throws TemplateModelException {
		
		Retinfo retinfo = new Retinfo();
		retinfo.setRetCode("100");
		retinfo.setRetMsg("Success");
		
		JSONObject reqbody = param.getJSONObject("reqbody");
		logger.info("reqbody is {}", reqbody);
		
		String userType = (String)request.getSession().getAttribute("userType");
				
		JSONObject req = new JSONObject();
//		req.put("shop", shopIndexService.selectByPrimaryKey(shopId));
		req.put("userType", userType);
		
		model.put("retinfo", retinfo);
		model.put("body", req.toString());
	}

	public void queryShop(HttpServletRequest request, HttpServletResponse response, JSONObject param, ModelMap model)
			throws TemplateModelException {
		
		Retinfo retinfo = new Retinfo();
		retinfo.setRetCode("100");
		retinfo.setRetMsg("Success");
		
		JSONObject reqbody = param.getJSONObject("reqbody");
		logger.info("reqbody is {}", reqbody);
		
		String area = "999";
		if (reqbody.containsKey("area")) {
			area = reqbody.getString("area");
		}
		
		String shopName = "";
		if (reqbody.containsKey("shopName")) {
			shopName = reqbody.getString("shopName");
		}
		
		Map<String, Object> reqMap = new HashMap<String, Object>();
		if ("999".equals(area)) {
//			reqMap.put("shop", shopService.selectAll(shopName));
		}else {
//			reqMap.put("shop", shopService.selectLikeShops(area, shopName));
		}
		
		reqMap.put("assistant", (String)request.getSession().getAttribute("userType"));

		model.put("retinfo", retinfo);
		model.put("body", JSONObject.fromObject(reqMap).toString());
	}
	
	public static void main(String[] args) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("key1", "123");
		map.put("key2", "456");
		String tempString = AESCrypto.encrypt(JSONObject.fromObject(map).toString());
		System.out.println(tempString);
		
		JSONObject object = new JSONObject();
		object = JSONObject.fromObject(AESCrypto.decrypt("BEA6B24C2024DE026AB00ADB4C34F9CA1BF8805C20CC8CA3490D272E00124173"));
		System.out.println(object.get("key1"));
	}
}
