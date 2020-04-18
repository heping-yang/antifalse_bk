package com.yinqiao.af.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinqiao.af.mapper.OnlineStudyMapper;
import com.yinqiao.af.model.OnlineStudy;
import com.yinqiao.af.service.IOnlineStudyService;

@Service("onlineStudyService")
public class OnlineStudyServiceImpl implements IOnlineStudyService {
	
	@Autowired
	private OnlineStudyMapper onlineStudyMapper;

	@Override
	public void insert(OnlineStudy onlineStudy) {
		onlineStudyMapper.insert(onlineStudy);
	}

	@Override
	public void update(OnlineStudy onlineStudy) {
		onlineStudyMapper.update(onlineStudy);
	}
	
	@Override
	public OnlineStudy select(String id) {
		return onlineStudyMapper.select(id);
	}

	@Override
	public List<OnlineStudy> selectAll() {
		return onlineStudyMapper.selectAll();
	}

	@Override
	public List<OnlineStudy> selectByUser(String telnum) {
		return onlineStudyMapper.selectByUser(telnum);
	}

	@Override
	public void delete(String id) {
		onlineStudyMapper.delete(id);
	}

	@Override
	public List<Map<String,Object>> studyTime(String telnum) {
		return onlineStudyMapper.studyTime(telnum);
	}

}
