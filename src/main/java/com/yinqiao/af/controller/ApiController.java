package com.yinqiao.af.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yinqiao.af.business.BaseAction;
import com.yinqiao.af.utils.StringUtil;

@Controller
@RequestMapping(value = "/api")
public class ApiController implements ApplicationContextAware{
	
	
	private ApplicationContext applicationContext; // Spring应用上下文环境
	
	private static Logger logger = LoggerFactory.getLogger(ApiController.class);

	@RequestMapping(value = "/receive/{bipcode}", method = RequestMethod.POST)
	public String receive(HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable String bipcode) {
		try {
			String input = request.getParameter("data");
			model.put("input", input);
			JSONObject param = JSONObject.fromObject(input);
			String process_code = param.getJSONObject("request_head").getString("process_code");
			String method = param.getJSONObject("request_head").getString("method");
			
			logger.info("bipcode is {}" , bipcode);
			logger.info("method is {}" , method);
			logger.info("param is {}" , param);
			if(StringUtil.checkNull(method)){
				method = "receive";
			}
			
			BaseAction action = (BaseAction)applicationContext.getBean(process_code+"Api");
			Class<? extends BaseAction> clz =action.getClass();
			for(Method m : clz.getMethods()){
				if(m.getName().equals(method)){
					m.invoke(action, request, response, param, model);
				}
			}
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "api/main";
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
