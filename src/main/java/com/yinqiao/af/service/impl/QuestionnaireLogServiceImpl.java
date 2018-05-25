package com.yinqiao.af.service.impl;

import java.util.List;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yinqiao.af.mapper.QuestionnaireLogMapper;
import com.yinqiao.af.model.QuestionnaireLog;
import com.yinqiao.af.service.IQuestionnaireLogService;

@Service("questionnaireLogService")

public class QuestionnaireLogServiceImpl implements IQuestionnaireLogService {

	@Autowired
	private QuestionnaireLogMapper questionnaireLogMapper;
	
	public int deleteByPrimaryKey(String logsid) {
		return questionnaireLogMapper.deleteByPrimaryKey(logsid);
	}

	public int insert(QuestionnaireLog record) {
		return questionnaireLogMapper.insert(record);
	}

	public QuestionnaireLog selectByPrimaryKey(String logsid) {
		return questionnaireLogMapper.selectByPrimaryKey(logsid);
	}

	public List<QuestionnaireLog> selectAll() {
		return questionnaireLogMapper.selectAll();
	}

	public int updateByPrimaryKey(QuestionnaireLog record) {
		return questionnaireLogMapper.updateByPrimaryKey(record);
	}

	@Transactional
	public int insertRecords(JSONObject obj , String index) {
		try {
			QuestionnaireLog record = new QuestionnaireLog();
			record.setLogsid(UUID.randomUUID().toString().replaceAll("-", ""));
			record.setWjnums("WJ001");
			record.setQuestionid(index);
			for (int i = 1; i <= 20; i++) {
				record.setTestid(""+i);
				record.setTestanswer((String)obj.get(""+i));
			}
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}

	public String getIndex(String regName) {
		// TODO Auto-generated method stub
		return null;
	}	
}
