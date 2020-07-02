package com.yinqiao.af.service;

import java.util.List;
import java.util.Map;

import com.yinqiao.af.model.ExamHistory;

public interface IExamHistoryService {

	int deleteByPrimaryKey(String hId);

	int insert(ExamHistory record);

	ExamHistory selectByPrimaryKey(String hId);

	int updateByPrimaryKey(ExamHistory record);

	String isExist(String hId);

	List<ExamHistory> selectAll(int start, int end, String telnum,Integer isexam);

	List<Map<String, Object>> studyTime(String telnum);

	int countByScore(String telnum, int score);

	List<ExamHistory> selectByUser(String telnum, String examId, Integer status);
}
