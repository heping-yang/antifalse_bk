package com.yinqiao.af.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.yinqiao.af.model.QuestionnaireLog;

public interface IQuestionnaireLogService {
	
    int deleteByPrimaryKey(String logsid);

    int insert(QuestionnaireLog record);
    
    int insertRecords(JSONObject obj , String index);

    QuestionnaireLog selectByPrimaryKey(String logsid);

    List<QuestionnaireLog> selectAll();

    int updateByPrimaryKey(QuestionnaireLog record);
    
    String queryMaxIndex(String regCode);
}
