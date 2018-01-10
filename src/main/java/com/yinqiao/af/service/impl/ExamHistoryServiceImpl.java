package com.yinqiao.af.service.impl;

import java.util.List;

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

	public List<ExamHistory> selectAll() {
		return examHistoryMapper.selectAll();
	}

	public int updateByPrimaryKey(ExamHistory record) {
		return examHistoryMapper.updateByPrimaryKey(record);
	}
}
