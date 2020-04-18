package com.yinqiao.af.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinqiao.af.mapper.PracticeListMapper;
import com.yinqiao.af.mapper.PracticeQuesMapper;
import com.yinqiao.af.model.PracticeList;
import com.yinqiao.af.service.IPracticeListService;

@Service("practiceListService")
public class PracticeListServiceImpl implements IPracticeListService {

	@Autowired
	private PracticeListMapper practiceListMapper;

	private PracticeQuesMapper practiceQuesMapper;

	@Override
	public void insert(PracticeList practiceList) {
		practiceListMapper.insert(practiceList);
	}

	@Override
	public void update(PracticeList practiceList) {
		practiceListMapper.update(practiceList);
	}

	@Override
	public PracticeList select(String id) {
		return practiceListMapper.select(id);
	}

	@Override
	public List<PracticeList> selectAll() {
		return practiceListMapper.selectAll();
	}

	@Override
	public List<PracticeList> selectByTypeUser(String quesType, String userType) {
		return practiceListMapper.selectByTypeUser(quesType, userType);
	}

	@Override
	public void delete(String id) {
		practiceListMapper.delete(id);
		practiceQuesMapper.deleteByPractice(id);
	}

}
