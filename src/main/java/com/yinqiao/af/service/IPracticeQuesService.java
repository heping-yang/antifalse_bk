package com.yinqiao.af.service;

import java.util.List;

import com.yinqiao.af.model.PracticeQues;

public interface IPracticeQuesService {
	void insert(PracticeQues practiceHistory);

	void update(PracticeQues practiceHistory);

	PracticeQues select(String id);

	List<PracticeQues> selectAll();

	List<PracticeQues> selectByPractice(String practice);

	void delete(String id);

	void deleteByPractice(String practice);
}
