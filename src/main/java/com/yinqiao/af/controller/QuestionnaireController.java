package com.yinqiao.af.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yinqiao.af.service.IAnnouncementService;
import com.yinqiao.af.service.IQuestionnaireLogService;

@Controller
@RequestMapping(value = "/questionnaire")
public class QuestionnaireController {
	
	@Autowired
	private IQuestionnaireLogService questionnaireLogService;
	
	private static Logger logger = LoggerFactory.getLogger(QuestionnaireController.class);

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
//		request.setAttribute("announcements",announcementService.selectAll());
//		request.setAttribute("text","123");
		return "questionnaire/list";
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String notice(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
//		request.setAttribute("announcements",announcementService.selectAll());
//		request.setAttribute("text","123");
		return "questionnaire/index";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	@ResponseBody
	public String save(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		JSONObject obj = JSONObject.fromObject(request.getParameter("answer"));
		//String index =  getIndex((String)obj.get("1"));
		String index = "YCS001";
		if ("1".equals(questionnaireLogService.insertRecords(obj, index))) {
			return index;
		}else {
			return "fail";
		}
		
	}
	
	private String getIndex(String regName){
		String regCode = "";
		if ("A".equals(regName)) {
			regCode = "YCS";
		}else if ("B".equals(regName)) {
			regCode = "WZS";
		}
		return questionnaireLogService.getIndex(regCode);
	}
	
	public static void main(String[] args) {
		JSONObject obj =  JSONObject.fromObject("{1:'AB',2:'AD',3:'BD'}");
		System.out.println(obj.get("1"));
	}
}
