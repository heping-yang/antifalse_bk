package com.yinqiao.af.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinqiao.af.mapper.GradeMapper;
import com.yinqiao.af.model.Grade;
import com.yinqiao.af.service.IGradeService;

@Service("gradeService")
public class GradeServiceImpl implements IGradeService {

	@Autowired
	private GradeMapper gradeMapper;
	
	public int deleteByPrimaryKey(Long gradeid) {
		return gradeMapper.deleteByPrimaryKey(gradeid);
	}

	public int insert(Grade record) {
		return gradeMapper.insert(record);
	}

	public Grade selectByPrimaryKey(Long gradeid) {
		return gradeMapper.selectByPrimaryKey(gradeid);
	}

	public List<Grade> selectAll() {
		return gradeMapper.selectAll();
	}

	public int updateByPrimaryKey(Grade record) {
		return gradeMapper.updateByPrimaryKey(record);
	}

	public List<Grade> selectByIdcard(String idcard) {
		return gradeMapper.selectByIdcard(idcard);
	}

}
