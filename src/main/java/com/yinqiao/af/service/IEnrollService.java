package com.yinqiao.af.service;

import java.util.List;

import com.yinqiao.af.model.Enroll;

public interface IEnrollService {

    int deleteByPrimaryKey(String bmkid);

    int insert(Enroll record);

    Enroll selectByPrimaryKey(String bmkid);

    List<Enroll> selectAll();

    int updateByPrimaryKey(Enroll record);
    
    String queryIsEnrolled(String idcard);
}
