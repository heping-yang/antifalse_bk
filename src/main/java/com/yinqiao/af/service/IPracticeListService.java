package com.yinqiao.af.service;

import java.util.List;

import com.yinqiao.af.model.PracticeList;

public interface IPracticeListService {
	void insert(PracticeList practiceList);

	void update(PracticeList practiceList);

	PracticeList select(String id);

	List<PracticeList> selectAll();

	List<PracticeList> selectByTypeUser(String quesType, String userType);

	void delete(String id);
}
