package com.yinqiao.af.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yinqiao.af.model.PracticeHistory;

public interface PracticeHistoryMapper {
	void insert(PracticeHistory practiceHistory);

	void update(PracticeHistory practiceHistory);

	PracticeHistory select(String id);

	List<PracticeHistory> selectAll();

	List<PracticeHistory> selectByUser(String telnum);

	void delete(String id);

	List<Map<String,Object>> studyTime(@Param("telnum")String telnum);
}
