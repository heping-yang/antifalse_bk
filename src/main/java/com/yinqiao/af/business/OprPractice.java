package com.yinqiao.af.business;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinqiao.af.common.MyJsonConfig;
import com.yinqiao.af.model.ExamHistory;
import com.yinqiao.af.model.PracticeHistory;
import com.yinqiao.af.model.PracticeList;
import com.yinqiao.af.model.QuestionBank;
import com.yinqiao.af.service.IPracticeHistoryService;
import com.yinqiao.af.service.IPracticeListService;
import com.yinqiao.af.service.IQuestionBankService;
import com.yinqiao.af.utils.DataUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("practiceApi")
public class OprPractice extends BaseAction {
	@Autowired
	private IPracticeListService practiceListService;

	@Autowired
	private IPracticeHistoryService practiceHistoryService;

	@Autowired
	private IQuestionBankService questionBankService;

	public String practiceList(HttpServletRequest request, HttpServletResponse response) {
		String quesType = request.getParameter("quesType");
		String userType = request.getParameter("userType");
		JSONObject json = new JSONObject();
		List<PracticeList> list = practiceListService.selectByTypeUser(quesType, userType);
		json.put("dataList", JSONArray.fromObject(list));
		json.put("dataCount", list.size());
		return json.toString();
	}

	public String practiceQues(HttpServletRequest request, HttpServletResponse response) {
		String practice = request.getParameter("practice");
		JSONObject json = new JSONObject();
		JSONArray list = new JSONArray();
		List<QuestionBank> questions = questionBankService.selectByPractice(practice);
		if (questions != null && questions.size() > 0) {
			int i = 0;
			for (QuestionBank ques : questions) {
				list.add(modelConvert(ques, i));
				i++;
			}
		}
		json.put("dataList", list);
		json.put("dataCount", questions.size());
		return json.toString();
	}

	public String saveAnswer(HttpServletRequest request, HttpServletResponse response) {
		String openid = request.getParameter("openid");
		String telnum = request.getParameter("telnum");
		String examId = request.getParameter("examId");
		String examName = request.getParameter("examName");
		String examType = request.getParameter("examType");
		String userType = request.getParameter("userType");
		String answerRecord = request.getParameter("answerRecord");
		Integer score = Integer.parseInt(request.getParameter("score"));
		Integer times = Integer.parseInt(request.getParameter("times"));

		PracticeHistory practiceHistory = new PracticeHistory();
		practiceHistory.setId(UUID.randomUUID().toString());
		practiceHistory.setOpenid(openid);
		practiceHistory.setTelnum(telnum);
		practiceHistory.setExamId(examId);
		practiceHistory.setExamName(examName);
		practiceHistory.setExamType(examType);
		practiceHistory.setUserType(userType);
		practiceHistory.setAnswerRecord(answerRecord);
		practiceHistory.setScore(score);
		practiceHistory.setTimes(times);
		practiceHistory.setCreateTime(new Date());
		practiceHistory.setUpdateTime(new Date());

		practiceHistoryService.insert(practiceHistory);
		return practiceHistory.getId();
	}

	public String queryReport(HttpServletRequest request, HttpServletResponse response) {
		String hId = request.getParameter("hId");
		PracticeHistory practice = practiceHistoryService.select(hId);
		return JSONObject.fromObject(practice).toString();
	}

	public String queryWrongAnalysis(HttpServletRequest request, HttpServletResponse response) {
		JSONObject req = new JSONObject();
		String hId = request.getParameter("hId");
		req.put("wrongs", makeAnalysis(hId));
		req.put("report", JSONObject.fromObject(practiceHistoryService.select(hId)));
		return req.toString();
	}

	public String queryHistoryList(HttpServletRequest request, HttpServletResponse response) {
		String telnum = request.getParameter("telnum");
		List<PracticeHistory> list = practiceHistoryService.selectByUser(telnum);
		JSONArray arr = JSONArray.fromObject(list, MyJsonConfig.instance("yyyy-MM-dd"));
		JSONObject json = new JSONObject();
		json.put("list", arr);
		json.put("count", list.size());
		return json.toString();
	}

	private JSONArray makeAnalysis(String hId) {
		PracticeHistory practiceHistory = practiceHistoryService.select(hId);
		if (practiceHistory == null) {
			return null;
		}
		JSONObject jsonObject = JSONObject.fromObject(practiceHistory.getAnswerRecord());
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		JSONArray wrongsArray = new JSONArray();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject job = jsonArray.getJSONObject(i);
			Object result=job.get("result");
			if (result==null || !"1".equals(result.toString())) {
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
}
