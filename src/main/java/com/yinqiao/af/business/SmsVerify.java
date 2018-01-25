package com.yinqiao.af.business;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yinqiao.af.model.Retinfo;
import com.yinqiao.af.utils.SmsSendUtil;
import com.yinqiao.af.utils.StringUtil;

import freemarker.template.TemplateModelException;

@Service("smsVerifyApi")
public class SmsVerify extends BaseAction {
	
	private static Logger logger = LoggerFactory.getLogger(SmsVerify.class);

	public String send(HttpServletRequest request, HttpServletResponse response){
		JSONObject req = new JSONObject();
		String telnum = request.getParameter("telnum");
		String smscode = String.valueOf((int)((Math.random()*9+1)*100000));
		SmsSendUtil.SendSms(telnum, smscode);
		req.put("smscode", smscode);
		return req.toString();
	}
	
	public void verify(HttpServletRequest request, HttpServletResponse response, JSONObject param, ModelMap model)
			throws TemplateModelException {
		Retinfo retinfo = new Retinfo();
		retinfo.setRetCode("100");
		retinfo.setRetMsg("Success");
		
		Map<String,Object> body = new HashMap<String, Object>();
		
		JSONObject reqbody = param.getJSONObject("reqbody");
		logger.info("reqbody is {}", reqbody);

		String vid = reqbody.getString("vid");
		String getcode = reqbody.getString("inputCode");
		
//		if(valOps.get(vid)==null||!valOps.get(vid).equals(getcode)){
//			retinfo.setRetCode("999");
//			retinfo.setRetMsg("verify fail");
//		} else {
//			valOps.getOperations().delete(vid);
//		}
		
		model.put("retinfo", retinfo);
		model.put("body", JSONObject.fromObject(body).toString());
	}

}
