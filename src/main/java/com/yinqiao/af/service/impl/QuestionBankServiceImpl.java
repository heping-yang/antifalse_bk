package com.yinqiao.af.service.impl;

import java.util.List;

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
	
	public String isExist(String questionId){
		return questionBankMapper.isExist(questionId);
	}
	public String getExamCount(String examId){
		return questionBankMapper.getExamCount(examId);
	}
}
