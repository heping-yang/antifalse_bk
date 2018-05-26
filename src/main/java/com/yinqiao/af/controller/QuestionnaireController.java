package com.yinqiao.af.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
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
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public void save(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		System.out.println("save");
		JSONObject obj = JSONObject.fromObject(request.getParameter("answer"));
		JSONObject json = new JSONObject();
		String index =  getIndex((String)obj.get("1"));
		if (questionnaireLogService.insertRecords(obj, index) == 1) {
			json.put("res", "success");
			json.put("index", index);
		}else {
			json.put("res", "fail");
		}
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.print(json.toString());
		writer.close();	
	}
	
	private String getIndex(String regName){
		String regCode = "";
		if ("A".equals(regName)) {
			regCode = "YCS";
		}else if ("B".equals(regName)) {
			regCode = "WZS";
		}else if ("C".equals(regName)) {
			regCode = "QTX";
		}else if ("D".equals(regName)) {
			regCode = "GCZ";
		}else if ("E".equals(regName)) {
			regCode = "XQC";
		}else if ("F".equals(regName)) {
			regCode = "YSC";
		}else if ("G".equals(regName)) {
			regCode = "LMC";
		}else if ("H".equals(regName)) {
			regCode = "LFC";
		}
		String index = questionnaireLogService.queryMaxIndex(regCode);
		if (StringUtils.isBlank(index)) {
			return regCode + "001"; 
		}else {
			return regCode +  String.format("%03d", Integer.parseInt(index)+1);
		}
	}
	
	public static void main(String[] args) {
		JSONObject obj =  JSONObject.fromObject("{1:'AB',2:'AD',3:'BD'}");
		System.out.println(obj.get("1"));
	}
}
