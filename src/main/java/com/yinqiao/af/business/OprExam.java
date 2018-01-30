package com.yinqiao.af.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinqiao.af.model.ExamHistory;
import com.yinqiao.af.model.QuestionBank;
import com.yinqiao.af.service.IExamHistoryService;
import com.yinqiao.af.service.IExamService;
import com.yinqiao.af.service.IQuestionBankService;

@Service("examApi")
public class OprExam extends BaseAction{
	
	private static Logger logger = LoggerFactory.getLogger(OprExam.class);
	
	@Autowired
	private IExamService examService;
	
	@Autowired
	private IQuestionBankService questionBankService;
	
	@Autowired
	private IExamHistoryService examHistoryService;
	
	public String list(HttpServletRequest request, HttpServletResponse response){
		JSONObject req = new JSONObject();
		req.put("list", JSONArray.fromObject(examService.selectAll()).toString());
		req.put("typelist", JSONArray.fromObject(questionBankService.queryTypeQuestionCount()).toString());
		return req.toString();
	}
	
	public String queryAnswers(HttpServletRequest request, HttpServletResponse response){
		JSONObject req = new JSONObject();
		String hId = request.getParameter("hId");
		req.put("answers", JSONArray.fromObject(examHistoryService.selectByPrimaryKey(hId)).toString());
		return req.toString();
	}
	
	
	//类型题下一题
	public String queryNextQuestion(HttpServletRequest request, HttpServletResponse response){
		JSONObject req = new JSONObject();
		String examId = request.getParameter("examId");
		String index = request.getParameter("index");
		String userAnswer = request.getParameter("userAnswer");
		String result = request.getParameter("userResult");
		String hId = request.getParameter("hId");
		String type = request.getParameter("type");
		String examtype = request.getParameter("examtype");
		int offset = 0;
		if ("1".equals(questionBankService.isExist(getNextQId(examId,index,1)))) {
			offset = 1;
		}
		req.put("question", JSONArray.fromObject(modelConvert(questionBankService.selectByPrimaryKey(getNextQId(examId,index,offset)))).toString());
		req.put("index", getNextIndex(index));
		req.put("total", questionBankService.getExamCount(examId));
		req.put("symbol", "/");
		req.put("lastFlag", questionBankService.isExist(getNextQId(examId,index,offset+1)));
		req.put("questionCnt", questionBankService.getQuestionCount(examId));
		if (!"select".equals(type)) {
			if (!"0".equals(index)) {
				this.UpdateExamHistory(hId,index,result,userAnswer);
			}else{
				examHistoryService.insert(getNewExamHistory(examId, hId,examtype));
			}
		}
		return req.toString();
	}
	
	
	public String queryNextTypeQuestion(HttpServletRequest request, HttpServletResponse response){
		JSONObject req = new JSONObject();
		String examId = request.getParameter("examId");
		String index = request.getParameter("index");
		String userAnswer = request.getParameter("userAnswer");
		String result = request.getParameter("userResult");
		String hId = request.getParameter("hId");
		String type = request.getParameter("type");
		String examtype = request.getParameter("examtype");
		int offset = 0;
		HashMap< String, String> map = new HashMap<String, String>();
		map.put("type", examtype);
		map.put("examId", examId);
		map.put("questionId", getNextQId(examId,index,1));
		if ("1".equals(questionBankService.isTypeExist(map))) {
			offset = 1;
		}
		System.out.println(getNextQId(examId,index,offset));
		map.put("questionId", getNextQId(examId,index,offset));
		req.put("question", JSONArray.fromObject(modelConvert(questionBankService.queryTypeQuestion(map))).toString());
		req.put("index", getNextIndex(index));
		req.put("total", questionBankService.getOneTypeQuestionCount(examtype));
		req.put("symbol", "/");
		map.put("questionId", getNextQId(examId,index,offset+1));
		req.put("lastFlag", questionBankService.isTypeExist(map));
		req.put("questionCnt", questionBankService.getOneTypeQuestionCount(examtype));
		if (!"select".equals(type)) {
			if (!"0".equals(index)) {
				this.UpdateExamHistory(hId,index,result,userAnswer);
			}else{
				examHistoryService.insert(getNewExamHistory(examId, hId,examtype));
			}
		}
		return req.toString();
	}
	
	public String queryExamReport(HttpServletRequest request, HttpServletResponse response){
		JSONObject req = new JSONObject();
		String hId = request.getParameter("hId");
		makeReport(hId);
		req.put("report", JSONArray.fromObject(examHistoryService.selectByPrimaryKey(hId)).toString());
		return req.toString();
	}
	
	private void makeReport(String hId){
		ExamHistory examHistory = examHistoryService.selectByPrimaryKey(hId);
		if (examHistory.getTotalscore() == null || "".equals(examHistory.getTotalscore())) {
			JSONObject jsonObject = JSONObject.fromObject(examHistory.getAnswerRecord());
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			int rightCnt = 0;
			int wrongCnt = 0;
			String score = "0";
			List<String> tempList = new ArrayList<String>();
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject job = jsonArray.getJSONObject(i);
				if ("1".equals(job.get("result"))) {
					rightCnt++;
					tempList.add(getNextQId(examHistory.getExamId(),job.get("index")+"",0));
				}else {
					wrongCnt++;
				}
			}
			if (tempList.size()>0) {
				score = questionBankService.getScore(tempList);
			}
			System.out.println(score);
			jsonObject.put("rightCnt",rightCnt);
			jsonObject.put("wrongCnt",wrongCnt);
			examHistory.setAnswerRecord(jsonObject.toString());
			examHistory.setTotalscore(score);
			examHistoryService.updateByPrimaryKey(examHistory);
		}
	}
	
	public String queryExamWrongAnalysis(HttpServletRequest request, HttpServletResponse response){
		JSONObject req = new JSONObject();
		String hId = request.getParameter("hId");
		req.put("wrongs", makeAnalysis(hId).toString());
		req.put("report", JSONArray.fromObject(examHistoryService.selectByPrimaryKey(hId)).toString());
		return req.toString();
	}
	
	private JSONArray makeAnalysis(String hId){
		ExamHistory examHistory = examHistoryService.selectByPrimaryKey(hId);
		JSONObject jsonObject = JSONObject.fromObject(examHistory.getAnswerRecord());
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		JSONArray wrongsArray = new JSONArray();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject job = jsonArray.getJSONObject(i);
			if (!"1".equals(job.get("result"))) {
				QuestionBank qBank = new QuestionBank();
				if (examHistory.getExamId().indexOf("type") >= 0) {
					HashMap<String, String>  map =  new HashMap<String, String>();
					map.put("type", examHistory.getExamId().substring(examHistory.getExamId().length() - 1));
					map.put("questionId", getNextQId(examHistory.getExamId(),job.get("index")+"",0));
					qBank = questionBankService.queryTypeQuestion(map);
				}else {
					qBank = questionBankService.selectByPrimaryKey(getNextQId(examHistory.getExamId(),job.get("index")+"",0));
				}
				qBank = this.modelConvert(qBank);
				String tempStandard  = qBank.getStandard();
				job.put("standard", tempStandard);
				if(tempStandard != null && !"".equals(tempStandard) &&("2".equals(job.get("type")) || "4".equals(job.get("type")))){
				 for (int j = 0; j < tempStandard.length(); j++) {            
					 job.put("standard"+tempStandard.charAt(j), tempStandard.charAt(j));
					 }
				}
				job.put("content", qBank.getContent());
				job.put("answers", qBank.getAnswer());
				if ("".equals(job.get("answer"))) {
					job.put("answer", "无作答");
				}
				wrongsArray.add(job);
			}
		}
		return wrongsArray;
	}
	
	private void UpdateExamHistory(String hId,String index,String result,String userAnswer){
		ExamHistory examHistory = examHistoryService.selectByPrimaryKey(hId);
		JSONObject jsonObject = JSONObject.fromObject(examHistory.getAnswerRecord());
		((JSONObject)jsonObject.getJSONArray("data").get(Integer.parseInt(index)-1)).put("answer",userAnswer);
		((JSONObject)jsonObject.getJSONArray("data").get(Integer.parseInt(index)-1)).put("result",result);
		examHistory.setAnswerRecord(jsonObject.toString());
		examHistory.setUpdateTime(new Date());
		examHistoryService.updateByPrimaryKey(examHistory);
	}
	
	private ExamHistory getNewExamHistory(String examId,String hId,String type){
		ExamHistory examHistory = new ExamHistory();
		examHistory.setCreateTime(new Date());
		examHistory.setExamId(examId);
		examHistory.sethId(hId);
		//examHistory.setQuestionId(questionId);
		//.setTotalscore("");
		//examHistory.setUpdateTime(updateTime);
		//examHistory.setUsedtime(usedtime);
		//examHistory.setUserId(userId);
		examHistory.setAnswerRecord(getNewAnswerRecord(examId,type));
		return examHistory;
	}
	
	private String getNewAnswerRecord(String examId,String type){
        JSONObject jsonObject = new JSONObject();  
        jsonObject.put("rightCnt", "");
        jsonObject.put("wrongCnt", "");  
        JSONArray jsonArray = new JSONArray();  
        int count = 1;
        List<Map> list = null;
        int typeCnt = 0;
        if ("0".equals(type)) {
        	list = questionBankService.getQuestionCount(examId);
    		for (Map map : list) {
    			int i = Integer.parseInt(map.get("QUESTIONCNT").toString());
    			for (int j = 0; j < i; j++) {
    				JSONObject dataelem=new JSONObject();  
    		        dataelem.put("type", map.get("TYPE").toString());
    		        dataelem.put("index", count);
    		        dataelem.put("answer", "");  
    		        dataelem.put("result", "");
    		        count++;
    		        jsonArray.add(dataelem);  
    			}
    		}
		}else {
			typeCnt = Integer.parseInt(questionBankService.getOneTypeQuestionCount(type));
			for (int j = 0; j < typeCnt; j++) {
				JSONObject dataelem=new JSONObject();  
		        dataelem.put("type", type);
		        dataelem.put("index", count);
		        dataelem.put("answer", "");  
		        dataelem.put("result", "");
		        count++;
		        jsonArray.add(dataelem);  
			}
		}
        jsonObject.element("data", jsonArray);
        return jsonObject.toString();
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
//		try {
//			System.out.println(URLEncoder.encode("{comboType:daycard1}","UTF-8"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String questionId = "T01001";
//		System.out.println(questionId.substring(0,3));
        JSONObject jsonObject = new JSONObject();  
        jsonObject.put("ret", new Integer(0));  
        jsonObject.put("msg", "query");  
        JSONObject dataelem1=new JSONObject();  
        //{"deviceid":"SH01H20130002","latitude":"32.140","longitude":"118.640","speed":"","orientation":""}  
        dataelem1.put("type", "1");
        dataelem1.put("index", "1");
        dataelem1.put("answer", "A");  
        dataelem1.put("result", "1");  
   
        JSONObject dataelem2=new JSONObject();  
        //{"deviceid":"SH01H20130002","latitude":"32.140","longitude":"118.640","speed":"","orientation":""}  
        dataelem2.put("type", "1");
        dataelem2.put("index", "2");
        dataelem2.put("answer", "A");  
        dataelem2.put("result", "1");    
          
        // 返回一个JSONArray对象  
        JSONArray jsonArray = new JSONArray();  
          
        jsonArray.add(0, dataelem1);  
        jsonArray.add(1, dataelem2);  
        jsonObject.element("data", jsonArray); 
        ((JSONObject)jsonObject.getJSONArray("data").get(0)).put("answer","C");
        System.out.println(jsonObject.getJSONArray("data").get(0));
        System.out.println(jsonObject);
        
        String temp = "124563,12453364,";
        System.out.println(temp.substring(0,temp.length() -1 ));
        
        System.out.println("00001".substring("00001".length() -1));
	}
}
