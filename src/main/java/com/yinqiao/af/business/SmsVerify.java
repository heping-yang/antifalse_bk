package com.yinqiao.af.business;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yinqiao.af.model.Retinfo;
import com.yinqiao.af.utils.StringUtil;

import freemarker.template.TemplateModelException;

@Service("smsVerifyApi")
public class SmsVerify extends BaseAction {
	
	private static Logger logger = LoggerFactory.getLogger(SmsVerify.class);
	
//	@Autowired
//	private ISmsService smsService;
	
	@Resource(name="redisTemplate")
	private ValueOperations<String, Object> valOps;

	public void send(HttpServletRequest request, HttpServletResponse response, JSONObject param, ModelMap model)
			throws TemplateModelException {
		Retinfo retinfo = new Retinfo();
		retinfo.setRetCode("100");
		retinfo.setRetMsg("Success");
		
		Map<String,Object> body = new HashMap<String, Object>();
		
		JSONObject reqbody = param.getJSONObject("reqbody");
		logger.info("reqbody is {}", reqbody);

		String desc = reqbody.getString("desc");
		String telnum = reqbody.getString("telnum");
		if(StringUtil.checkNull(desc)){
			desc = "";
		}
		
		String smscode = String.valueOf((int)((Math.random()*9+1)*100000));
		String verify = UUID.randomUUID().toString();
		
//		SmsNotify sn = new SmsNotify();
//		sn.setTelnum(telnum);
//		sn.setContent("尊敬的用户，您本登录" + desc + "操作的短信验证码为：" + smscode + "，有效时间为2分钟，仅限本次使用");
//		smsService.sendSms(sn);
		valOps.set(verify, smscode, 2, TimeUnit.MINUTES);//2分钟有效
		
		body.put("vid", verify);
		model.put("retinfo", retinfo);
		model.put("body", JSONObject.fromObject(body).toString());
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
		
		if(valOps.get(vid)==null||!valOps.get(vid).equals(getcode)){
			retinfo.setRetCode("999");
			retinfo.setRetMsg("verify fail");
		} else {
			valOps.getOperations().delete(vid);
		}
		
		model.put("retinfo", retinfo);
		model.put("body", JSONObject.fromObject(body).toString());
	}

}
