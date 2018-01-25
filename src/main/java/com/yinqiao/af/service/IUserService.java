package com.yinqiao.af.service;

import java.util.List;

import com.yinqiao.af.model.User;

public interface IUserService {

    int deleteByPrimaryKey(String telnum);

    int insert(User record);

    User selectByPrimaryKey(String telnum);

    List<User> selectAll();

    int updateByPrimaryKey(User record);
}
