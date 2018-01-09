package com.yinqiao.af.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.yinqiao.af.business.BaseAction;
import com.yinqiao.af.utils.StringUtil;

@Controller
@RequestMapping(value = "/api")
public class ApiController implements ApplicationContextAware{
	
	
	private ApplicationContext applicationContext; // Spring应用上下文环境
	
	private static Logger logger = LoggerFactory.getLogger(ApiController.class);

	@ResponseBody
	@RequestMapping(value = "/{process_code}", method = RequestMethod.GET , produces = "application/json; charset=utf-8")
	public String get(HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable String process_code) {
		try {
			String method = request.getParameter("method");
			System.out.println(method);
			logger.info("bipcode is {}" , process_code);
			Enumeration enu=request.getParameterNames();  
			while(enu.hasMoreElements()){  
				String paraName=(String)enu.nextElement();  
				logger.info("param is "+ paraName+": "+request.getParameter(paraName));  
			}
			if(StringUtil.checkNull(method)){
				method = "receive";
			}
			BaseAction action = (BaseAction)applicationContext.getBean(process_code+"Api");
			Class<? extends BaseAction> clz =action.getClass();
			for(Method m : clz.getMethods()){
				if(m.getName().equals(method)){
					return (String)m.invoke(action, request, response);
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
		return process_code + "处理失败";
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
