package com.yinqiao.af.business;

import java.awt.List;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinqiao.af.model.QuestionBank;
import com.yinqiao.af.service.IExamService;
import com.yinqiao.af.service.IQuestionBankService;

@Service("examApi")
public class OprExam extends BaseAction{
	
	private static Logger logger = LoggerFactory.getLogger(OprExam.class);
	
	@Autowired
	private IExamService examService;
	
	@Autowired
	private IQuestionBankService questionBankService;
	
	public String list(HttpServletRequest request, HttpServletResponse response){
		JSONObject req = new JSONObject();
		System.out.println(JSONArray.fromObject(examService.selectAll()).toString());
		req.put("list", JSONArray.fromObject(examService.selectAll()).toString());
		return req.toString();
	}
	
	public String queryNextQuestion(HttpServletRequest request, HttpServletResponse response){
		JSONObject req = new JSONObject();
		String examId = request.getParameter("examId");
		String index = request.getParameter("index");
		System.out.println(JSONArray.fromObject(examService.selectAll()).toString());
		req.put("question", JSONArray.fromObject(modelConvert(questionBankService.selectByPrimaryKey(getNextQId(examId,index,1)))).toString());
		req.put("index", getNextIndex(index));
		req.put("total", questionBankService.getExamCount(examId));
		req.put("lastFlag", questionBankService.isExist(getNextQId(examId,index,2)));
		return req.toString();
	}
	
	private String getNextQId(String examId,String index,int num){
		String questionId = "";
		if (StringUtils.isNotEmpty(index)) {
			questionId = examId + StringUtils.leftPad(Integer.parseInt(index)+num+"", 3, "0");
		}else {
			questionId = examId + "001";
		}
		return questionId;
	}
	
	private String getNextIndex(String index){
		if (StringUtils.isNotEmpty(index)) {
			index = Integer.parseInt(index) + 1 + "";
		}else {
			index = "1";
		}
		return index;
	}
	
	private QuestionBank modelConvert(QuestionBank qb){
		ArrayList<Map> list = new ArrayList<Map>();
		String answer = qb.getAnswer();
		System.out.println(answer);
		String[] answerArray = answer.split("##");
		for (int i = 0; i < answerArray.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			String option = "";
			switch (i) {
			case 0:
				option = "A";
				break;
			case 1:
				option = "B";
				break;
			case 2:
				option = "C";
				break;
			case 3:
				option = "D";
				break;
			case 4:
				option = "E";
				break;
			case 5:
				option = "F";
				break;
			case 6:
				option = "G";
				break;
			case 7:
				option = "H";
				break;
			case 8:
				option = "I";
				break;
			case 9:
				option = "J";
				break;
			default:
				break;
			}
			map.put("answer", answerArray[i]);
			map.put("option", option);
			list.add(map);
		}
		System.out.println(JSONArray.fromObject(list).toString());
		qb.setAnswer(JSONArray.fromObject(list).toString());
		return qb;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(URLEncoder.encode("{comboType:daycard1}","UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String questionId = "T01001";
		System.out.println(questionId.substring(0,3));
	}
}
