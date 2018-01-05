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

	public String receive(HttpServletRequest request, HttpServletResponse response)
			throws TemplateModelException {

		Map<String,Object> body = new HashMap<String, Object>();
		body.put("status", "success");
		return JSONObject.fromObject(body).toString();
	}

}
