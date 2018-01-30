package com.yinqiao.af.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinqiao.af.mapper.EnrollMapper;
import com.yinqiao.af.model.Enroll;
import com.yinqiao.af.service.IEnrollService;

@Service("enrollService")
public class EnrollServiceImpl implements IEnrollService {

	@Autowired
	private EnrollMapper enrollMapper;
	
	public int deleteByPrimaryKey(String bmkid) {
		return enrollMapper.deleteByPrimaryKey(bmkid);
	}

	public int insert(Enroll record) {
		return enrollMapper.insert(record);
	}

	public Enroll selectByPrimaryKey(String bmkid) {
		return enrollMapper.selectByPrimaryKey(bmkid);
	}

	public List<Enroll> selectAll() {
		return enrollMapper.selectAll();
	}

	public int updateByPrimaryKey(Enroll record) {
		return enrollMapper.updateByPrimaryKey(record);
	}

	public String queryIsEnrolled(String idcard) {
		return enrollMapper.queryIsEnrolled(idcard);
	}

}
