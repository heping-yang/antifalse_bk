package com.yinqiao.af.service;

import java.util.List;
import java.util.Map;

import com.yinqiao.af.model.PracticeHistory;

public interface IPracticeHistoryService {
	void insert(PracticeHistory practiceHistory);

	void update(PracticeHistory practiceHistory);

	PracticeHistory select(String id);

	List<PracticeHistory> selectAll();

	List<PracticeHistory> selectByUser(String telnum);

	void delete(String id);

	List<Map<String,Object>> studyTime(String telnum);
}
