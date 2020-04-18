package com.yinqiao.af.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinqiao.af.model.ExamHistory;
import com.yinqiao.af.model.QuestionBank;
import com.yinqiao.af.service.IExamHistoryService;
import com.yinqiao.af.service.IExamService;
import com.yinqiao.af.service.IQuestionBankService;
import com.yinqiao.af.utils.DataUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("examApi")
public class OprExam extends BaseAction {

	@Autowired
	private IExamService examService;

	@Autowired
	private IQuestionBankService questionBankService;

	@Autowired
	private IExamHistoryService examHistoryService;

	public String list(HttpServletRequest request, HttpServletResponse response) {
		JSONObject req = new JSONObject();
		req.put("list", JSONArray.fromObject(examService.selectAll()).toString());
		req.put("typelist", JSONArray.fromObject(questionBankService.queryTypeQuestionCount()).toString());
		return req.toString();
	}

	public String queryQuestions(HttpServletRequest request, HttpServletResponse response) {
		String pageNo = request.getParameter("pageNo");
		String examId = request.getParameter("examId");
		String examType = request.getParameter("examType");
		int start = 0;
		if (!DataUtil.isEmpty(pageNo)) {
			start = Integer.parseInt(pageNo) * 100;
		}
		int end = start + 100;

		if ("0".equals(examType)) {
			examType = null;
		}

		if (examId.startsWith("type")) {
			examId = null;
		}

		List<JSONObject> list = new ArrayList<JSONObject>();
		List<QuestionBank> questions = questionBankService.selectQuestions(start, end, examId, examType);
		if (questions != null && questions.size() > 0) {
			int i = 0;
			for (QuestionBank ques : questions) {
				list.add(modelConvert(ques, i));
				i++;
			}
		}

		JSONObject ret = new JSONObject();
		ret.put("questions", list);
		int total = questions.size();

		if (total >= 100) {
			// 查总量
			total = questionBankService.selectQuestionsCnt(examId, examType);
		}
		ret.put("total", total);
		return ret.toString();
	}

	public String saveAnswer(HttpServletRequest request, HttpServletResponse response) {
		String examId = request.getParameter("examId");
		String examName = request.getParameter("examName");
		String telnum = request.getParameter("telnum");
		String examType = request.getParameter("examType");
		String indexnum = request.getParameter("indexnum");
		String totalscore = request.getParameter("totalscore");
		String surplustime = request.getParameter("surplustime");
		String usedtime = request.getParameter("usedtime");
		String answerRecord = request.getParameter("answerRecord");
		ExamHistory examHistory = new ExamHistory();
		examHistory.sethId(UUID.randomUUID().toString());
		examHistory.setCreateTime(new Date());
		examHistory.setExamId(examId);
		examHistory.setExamName(examName);
		examHistory.setExamType(examType);
		examHistory.setIndexnum(indexnum);
		examHistory.setAnswerRecord(answerRecord);
		examHistory.setSurplustime(surplustime);
		examHistory.setTelnum(telnum);
		examHistory.setTotalscore(totalscore);
		examHistory.setUsedtime(Integer.parseInt(usedtime));

		examHistoryService.insert(examHistory);

		// 查询大于80分的模拟次数
		int count = examHistoryService.countByScore(telnum, 80);
		JSONObject json = new JSONObject();
		json.put("count", count);
		json.put("id", examHistory.gethId());

		return json.toString();
	}

	public String canOnlineApply(HttpServletRequest request, HttpServletResponse response) {
		String telnum = request.getParameter("telnum");
		int count = examHistoryService.countByScore(telnum, 80);
		boolean flag = false;
		if (count >= 3) {
			flag = true;
		}
		return String.valueOf(flag);
	}

	public String queryExamReport(HttpServletRequest request, HttpServletResponse response) {
		String hId = request.getParameter("hId");
		return JSONObject.fromObject(examHistoryService.selectByPrimaryKey(hId)).toString();
	}

	public String queryExamWrongAnalysis(HttpServletRequest request, HttpServletResponse response) {
		JSONObject req = new JSONObject();
		String hId = request.getParameter("hId");
		req.put("wrongs", makeAnalysis(hId).toString());
		req.put("report", JSONArray.fromObject(examHistoryService.selectByPrimaryKey(hId)).toString());
		return req.toString();
	}

	private JSONArray makeAnalysis(String hId) {
		ExamHistory examHistory = examHistoryService.selectByPrimaryKey(hId);
		JSONObject jsonObject = JSONObject.fromObject(examHistory.getAnswerRecord());
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		JSONArray wrongsArray = new JSONArray();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject job = jsonArray.getJSONObject(i);
			Object result = job.get("result");
			if (result == null || !"1".equals(result.toString())) {
				QuestionBank qBank = questionBankService.selectByPrimaryKey(job.optString("questionId"));
				String tempStandard = qBank.getStandard();
				job.put("standard", tempStandard);
				if (tempStandard != null && !"".equals(tempStandard)
						&& ("2".equals(job.get("type")) || "4".equals(job.get("type")))) {
					for (int j = 0; j < tempStandard.length(); j++) {
						job.put("standard" + tempStandard.charAt(j), tempStandard.charAt(j));
					}
				}
				job.put("content", qBank.getContent());
				JSONArray arr = new JSONArray();
				String answer = qBank.getAnswer();
				String[] answerArray = answer.split("##");
				for (int j = 0; j < answerArray.length; j++) {
					String option = "";
					switch (j) {
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
					JSONObject item = new JSONObject();
					item.put("answer", answerArray[j]);
					item.put("option", option);
					arr.add(item);
				}
				job.put("answers", arr);
				if ("".equals(job.get("answer"))) {
					job.put("answer", "无作答");
				}
				wrongsArray.add(job);
			}
		}
		return wrongsArray;
	}

	private JSONObject modelConvert(QuestionBank qb, int index) {
		JSONObject data = null;
		if (qb == null) {
			return data;
		}
		data = JSONObject.fromObject(qb);
		JSONArray arr = new JSONArray();
		String answer = qb.getAnswer();
		String[] answerArray = new String[] {};
		if (!DataUtil.isEmpty(answer)) {
			answerArray = answer.split("##");
			for (int i = 0; i < answerArray.length; i++) {
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
				JSONObject item = new JSONObject();
				item.put("answer", answerArray[i]);
				item.put("option", option);
				arr.add(item);
			}
		} else {
			if ("3".equals(qb.getType())) {
				JSONObject item = new JSONObject();
				item.put("answer", "对");
				item.put("option", "A");
				arr.add(item);

				item = new JSONObject();
				item.put("answer", "错");
				item.put("option", "B");
				arr.add(item);
			}
		}
		data.put("answers", arr);
		data.put("index", index);
		return data;
	}

	private String secToTime(long stime, long etime) {
		String timeStr = null;
		try {
			long time = (stime - etime) / 1000;
			long minute = 0;
			long second = 0;
			if (time <= 0)
				return "00:00";
			else if (time >= 1800) {
				return "30:00";
			} else {
				minute = time / 60;
				if (minute < 60) {
					second = time % 60;
					timeStr = unitFormat(minute) + ":" + unitFormat(second);
				}
			}
		} catch (Exception e) {
			return timeStr;
		}
		return timeStr;
	}

	private String unitFormat(long i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Long.toString(i);
		else
			retStr = "" + i;
		return retStr;
	}

	public String queryHistoryList(HttpServletRequest request, HttpServletResponse response) {
		String telnum = request.getParameter("telnum");
		String pageNo = request.getParameter("pageNo");
		JSONObject req = new JSONObject();
		int start = 0;
		int end = 10;
		if (!DataUtil.isEmpty(pageNo)) {
			start = Integer.parseInt(pageNo) * 10;
			end = start + 10;
		}
		req.put("list", JSONArray.fromObject(examHistoryService.selectAll(start, end, telnum)).toString());
		return req.toString();
	}

	public static void main(String[] args) {
		// try {
		// System.out.println(URLEncoder.encode("{comboType:daycard1}","UTF-8"));
		// } catch (UnsupportedEncodingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// String questionId = "T01001";
		// System.out.println(questionId.substring(0,3));
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ret", new Integer(0));
		jsonObject.put("msg", "query");
		JSONObject dataelem1 = new JSONObject();
		// {"deviceid":"SH01H20130002","latitude":"32.140","longitude":"118.640","speed":"","orientation":""}
		dataelem1.put("type", "1");
		dataelem1.put("index", "1");
		dataelem1.put("answer", "A");
		dataelem1.put("result", "1");

		JSONObject dataelem2 = new JSONObject();
		// {"deviceid":"SH01H20130002","latitude":"32.140","longitude":"118.640","speed":"","orientation":""}
		dataelem2.put("type", "1");
		dataelem2.put("index", "2");
		dataelem2.put("answer", "A");
		dataelem2.put("result", "1");

		// 返回一个JSONArray对象
		JSONArray jsonArray = new JSONArray();

		jsonArray.add(0, dataelem1);
		jsonArray.add(1, dataelem2);
		jsonObject.element("data", jsonArray);
		((JSONObject) jsonObject.getJSONArray("data").get(0)).put("answer", "C");
		System.out.println(jsonObject.getJSONArray("data").get(0));
		System.out.println(jsonObject);

		String temp = "124563,12453364,";
		System.out.println(temp.substring(0, temp.length() - 1));

		System.out.println("00001".substring("00001".length() - 1));
	}
}
