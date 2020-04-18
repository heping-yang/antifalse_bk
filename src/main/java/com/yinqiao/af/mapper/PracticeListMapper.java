package com.yinqiao.af.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yinqiao.af.model.PracticeList;

public interface PracticeListMapper {
	void insert(PracticeList practiceList);

	void update(PracticeList practiceList);

	PracticeList select(String id);

	List<PracticeList> selectAll();

	List<PracticeList> selectByTypeUser(@Param("quesType") String quesType, @Param("userType") String userType);

	void delete(String id);
}
