package com.yinqiao.af.service;

import java.util.List;
import java.util.Map;

import com.yinqiao.af.model.OnlineStudy;

public interface IOnlineStudyService {
	void insert(OnlineStudy onlineStudy);

	void update(OnlineStudy onlineStudy);
	
	OnlineStudy select(String id);
	
	List<OnlineStudy> selectAll();

	List<OnlineStudy> selectByUser(String telnum);
	
	void delete(String id);

	List<Map<String,Object>> studyTime(String telnum);
}
