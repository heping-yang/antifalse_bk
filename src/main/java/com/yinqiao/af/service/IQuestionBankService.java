package com.yinqiao.af.service;

import java.util.List;
import java.util.Map;

import com.yinqiao.af.model.QuestionBank;

public interface IQuestionBankService {

    int deleteByPrimaryKey(String questionId);

    int insert(QuestionBank record);

    QuestionBank selectByPrimaryKey(String questionId);

    List<QuestionBank> selectAll();

    int updateByPrimaryKey(QuestionBank record);
    
    //查询当前题是否存在
    String isExist(String questionId);
    
	String getExamCount(String examId);
	
	List<Map> getQuestionCount(String examId);
	
	//根据做对答案参数获取试题分数
	String getScore(String rights);
}
