package com.yinqiao.af.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yinqiao.af.model.OnlineStudy;

public interface OnlineStudyMapper {
	void insert(OnlineStudy onlineStudy);

	void update(OnlineStudy onlineStudy);
	
	OnlineStudy select(String id);

	List<OnlineStudy> selectAll();

	List<OnlineStudy> selectByUser(String telnum);

	void delete(String id);

	List<Map<String,Object>> studyTime(@Param("telnum")String telnum);
}
