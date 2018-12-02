package com.yinqiao.af.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yinqiao.af.model.QuestionBank;

public interface IQuestionBankService {

	int deleteByPrimaryKey(String questionId);

	int insert(QuestionBank record);

	QuestionBank selectByPrimaryKey(String questionId);

	List<QuestionBank> selectAll();

	int updateByPrimaryKey(QuestionBank record);

	// 查询当前题是否存在
	String isExist(String questionId);

	String getExamCount(String examId);

	List<Map> getQuestionCount(String examId);

	// 根据做对答案参数获取试题分数
	String getScore(List<String> rights);

	List<Map> queryTypeQuestionCount();

	// 按类型
	String isTypeExist(HashMap<String, String> map);

	String getTypeExamCount(HashMap<String, String> map);

	List<Map> getTypeQuestionCount(HashMap<String, String> map);

	String getOneTypeQuestionCount(String type);

	List<QuestionBank> selectQuestions(int start, int end, String examId, String examType);

	int selectQuestionsCnt(String examId, String examType);
}
