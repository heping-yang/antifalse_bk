package com.yinqiao.af.business;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinqiao.af.service.IExamService;

@Service("examApi")
public class OprExam extends BaseAction{
	
	private static Logger logger = LoggerFactory.getLogger(OprExam.class);
	
	@Autowired
	private IExamService examService;
	
	public String list(HttpServletRequest request, HttpServletResponse response){
		JSONObject req = new JSONObject();
		System.out.println(JSONArray.fromObject(examService.selectAll()).toString());
		req.put("list", JSONArray.fromObject(examService.selectAll()).toString());
		return req.toString();
	}
}
