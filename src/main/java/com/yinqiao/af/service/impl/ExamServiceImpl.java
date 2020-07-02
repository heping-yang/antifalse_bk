package com.yinqiao.af.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinqiao.af.mapper.ExamListMapper;
import com.yinqiao.af.model.ExamList;
import com.yinqiao.af.service.IExamService;

@Service("examService")
public class ExamServiceImpl implements IExamService {

	@Autowired
	private ExamListMapper examListMapper;

	public int deleteByPrimaryKey(String examId) {
		return examListMapper.deleteByPrimaryKey(examId);
	}

	public int insert(ExamList record) {
		return examListMapper.insert(record);
	}

	public ExamList selectByPrimaryKey(String examId) {
		return examListMapper.selectByPrimaryKey(examId);
	}

	public List<ExamList> selectAll() {
		return examListMapper.selectAll();
	}

	public int updateByPrimaryKey(ExamList record) {
		return examListMapper.updateByPrimaryKey(record);
	}

	public String queryExamNameById(String examId) {
		return examListMapper.queryExamNameById(examId);
	}

	@Override
	public List<ExamList> selectByUser(String telnum) {
		return examListMapper.selectByUser(telnum);
	}
}
