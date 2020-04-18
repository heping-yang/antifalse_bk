package com.yinqiao.af.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinqiao.af.mapper.PracticeQuesMapper;
import com.yinqiao.af.model.PracticeQues;
import com.yinqiao.af.service.IPracticeQuesService;

@Service("PracticeQuesService")
public class PracticeQuesServiceImpl implements IPracticeQuesService {

	@Autowired
	private PracticeQuesMapper practiceQuesMapper;

	@Override
	public void insert(PracticeQues practiceQues) {
		practiceQuesMapper.insert(practiceQues);
	}

	@Override
	public void update(PracticeQues practiceQues) {
		practiceQuesMapper.update(practiceQues);
	}

	@Override
	public PracticeQues select(String id) {
		return practiceQuesMapper.select(id);
	}

	@Override
	public List<PracticeQues> selectAll() {
		return practiceQuesMapper.selectAll();
	}

	@Override
	public List<PracticeQues> selectByPractice(String practice) {
		return practiceQuesMapper.selectByPractice(practice);
	}

	@Override
	public void delete(String id) {
		practiceQuesMapper.delete(id);
	}

	@Override
	public void deleteByPractice(String practice) {
		practiceQuesMapper.deleteByPractice(practice);		
	}

}
