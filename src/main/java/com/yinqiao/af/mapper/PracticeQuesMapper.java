package com.yinqiao.af.mapper;

import java.util.List;

import com.yinqiao.af.model.PracticeQues;

public interface PracticeQuesMapper {
	void insert(PracticeQues practiceQues);

	void update(PracticeQues practiceQues);

	PracticeQues select(String id);

	List<PracticeQues> selectAll();

	List<PracticeQues> selectByPractice(String practice);

	void delete(String id);
	
	void deleteByPractice(String practice);
}
