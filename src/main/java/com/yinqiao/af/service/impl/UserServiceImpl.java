package com.yinqiao.af.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinqiao.af.mapper.UserMapper;
import com.yinqiao.af.model.User;
import com.yinqiao.af.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;
	
	public int deleteByPrimaryKey(String telnum) {
		return userMapper.deleteByPrimaryKey(telnum);
	}

	public int insert(User record) {
		return userMapper.insert(record);
	}

	public User selectByPrimaryKey(String telnum) {
		return userMapper.selectByPrimaryKey(telnum);
	}

	public List<User> selectAll() {
		return userMapper.selectAll();
	}

	public int updateByPrimaryKey(User record) {
		return userMapper.updateByPrimaryKey(record);
	}

	public String queryUserIsEffective(String idcard) {
		return userMapper.queryUserIsEffective(idcard);
	}

	public String queryUserCnt(String telnum) {
		return userMapper.queryUserCnt(telnum);
	}

}
