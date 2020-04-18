package com.yinqiao.af.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinqiao.af.mapper.PracticeHistoryMapper;
import com.yinqiao.af.model.PracticeHistory;
import com.yinqiao.af.service.IPracticeHistoryService;

@Service("practiceHistoryService")
public class PracticeHistoryServiceImpl implements IPracticeHistoryService {
	@Autowired
	private PracticeHistoryMapper practiceHistoryMapper;

	@Override
	public void insert(PracticeHistory practiceHistory) {
		practiceHistoryMapper.insert(practiceHistory);
	}

	@Override
	public void update(PracticeHistory practiceHistory) {
		practiceHistoryMapper.update(practiceHistory);
	}

	@Override
	public PracticeHistory select(String id) {
		return practiceHistoryMapper.select(id);
	}

	@Override
	public List<PracticeHistory> selectAll() {
		return practiceHistoryMapper.selectAll();
	}

	@Override
	public List<PracticeHistory> selectByUser(String telnum) {
		return practiceHistoryMapper.selectByUser(telnum);
	}

	@Override
	public void delete(String id) {
		practiceHistoryMapper.delete(id);
	}

	@Override
	public List<Map<String,Object>> studyTime(String telnum) {
		return practiceHistoryMapper.studyTime(telnum);
	}

}
