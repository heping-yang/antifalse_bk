package com.yinqiao.af.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinqiao.af.mapper.QuestionBankMapper;
import com.yinqiao.af.model.QuestionBank;
import com.yinqiao.af.service.IQuestionBankService;

@Service("questionBankService")
public class QuestionBankServiceImpl implements IQuestionBankService {

	@Autowired
	private QuestionBankMapper questionBankMapper;

	public int deleteByPrimaryKey(String questionId) {
		return questionBankMapper.deleteByPrimaryKey(questionId);
	}

	public int insert(QuestionBank record) {
		return questionBankMapper.insert(record);
	}

	public QuestionBank selectByPrimaryKey(String questionId) {
		return questionBankMapper.selectByPrimaryKey(questionId);
	}

	public List<QuestionBank> selectAll() {
		return questionBankMapper.selectAll();
	}

	public int updateByPrimaryKey(QuestionBank record) {
		return questionBankMapper.updateByPrimaryKey(record);
	}

	public String isExist(String questionId) {
		return questionBankMapper.isExist(questionId);
	}

	public String getExamCount(String examId) {
		return questionBankMapper.getExamCount(examId);
	}

	public List<Map> getQuestionCount(String examId) {
		return questionBankMapper.getQuestionCount(examId);
	}

	public String getScore(List<String> rights) {
		return questionBankMapper.getScore(rights);
	}

	public List<Map> queryTypeQuestionCount() {
		return questionBankMapper.queryTypeQuestionCount();
	}

	public String isTypeExist(HashMap<String, String> map) {
		return questionBankMapper.isTypeExist(map);
	}

	public String getTypeExamCount(HashMap<String, String> map) {
		return questionBankMapper.getTypeExamCount(map);
	}

	public List<Map> getTypeQuestionCount(HashMap<String, String> map) {
		return questionBankMapper.getTypeQuestionCount(map);
	}

	public String getOneTypeQuestionCount(String type) {
		return questionBankMapper.getOneTypeQuestionCount(type);
	}

	@Override
	public List<QuestionBank> selectQuestions(int start, int end, String examId, String examType) {
		return questionBankMapper.selectQuestions(start, end, examId, examType);
	}

	@Override
	public int selectQuestionsCnt(String examId, String examType) {
		return questionBankMapper.selectQuestionsCnt(examId, examType);
	}

	@Override
	public List<QuestionBank> selectByPractice(String practice) {
		return questionBankMapper.selectByPractice(practice);
	}

}
