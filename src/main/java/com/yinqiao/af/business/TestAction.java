package com.yinqiao.af.business;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yinqiao.af.model.Retinfo;

import freemarker.template.TemplateModelException;
import net.sf.json.JSONObject;

@Service("testApi")
public class TestAction extends BaseAction {
	
	private static Logger logger = LoggerFactory.getLogger(TestAction.class);

	public void receive(HttpServletRequest request, HttpServletResponse response, JSONObject param, ModelMap model)
			throws TemplateModelException {
		/*Session authSession= getAuthSession("13d97289-2716-4fea-bb52-973f13d9d156");
		logger.info("authSession is {}", authSession);
		if(authSession!=null){
			logger.info("authenticated is {}", authSession.getAttribute("authenticated"));
			logger.info("passport is {}", authSession.getAttribute("passport"));
			logger.info("provider is {}", authSession.getAttribute("provider"));
			Passport passport= authSession.getAttribute("passport");
			logger.info("openid is {}", passport.getIdentifier());
		}*/
		Retinfo retinfo = new Retinfo();
		retinfo.setRetCode("100");
		retinfo.setRetMsg("Success，成�?");
		
		JSONObject reqbody = param.getJSONObject("reqbody");
		logger.info("reqbody is {}", reqbody);
		
		Map<String,Object> body = new HashMap<String, Object>();
		body.put("status", "success");
		model.put("retinfo", retinfo);
		model.put("body", JSONObject.fromObject(body).toString());
	}

}
