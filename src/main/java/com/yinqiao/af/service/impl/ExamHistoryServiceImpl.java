package com.yinqiao.af.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinqiao.af.mapper.ExamHistoryMapper;
import com.yinqiao.af.model.ExamHistory;
import com.yinqiao.af.service.IExamHistoryService;

@Service("examHistoryService")
public class ExamHistoryServiceImpl implements IExamHistoryService {

	@Autowired
	private ExamHistoryMapper examHistoryMapper;

	public int deleteByPrimaryKey(String hId) {
		return examHistoryMapper.deleteByPrimaryKey(hId);
	}

	public int insert(ExamHistory record) {
		return examHistoryMapper.insert(record);
	}

	public ExamHistory selectByPrimaryKey(String hId) {
		return examHistoryMapper.selectByPrimaryKey(hId);
	}

	public List<ExamHistory> selectAll(int start, int end, String telnum, Integer isexam) {
		return examHistoryMapper.selectAll(start, end, telnum, isexam);
	}

	public int updateByPrimaryKey(ExamHistory record) {
		return examHistoryMapper.updateByPrimaryKey(record);
	}

	public String isExist(String hId) {
		return examHistoryMapper.isExist(hId);
	}

	@Override
	public List<Map<String, Object>> studyTime(String telnum) {
		return examHistoryMapper.studyTime(telnum);
	}

	@Override
	public int countByScore(String telnum, int score) {
		return examHistoryMapper.countByScore(telnum, score);
	}

	@Override
	public List<ExamHistory> selectByUser(String telnum, String examId, Integer status) {
		return examHistoryMapper.selectByUser(telnum, examId, status);
	}
}
