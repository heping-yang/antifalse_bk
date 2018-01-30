package com.yinqiao.af.service;

import java.util.List;

import com.yinqiao.af.model.Grade;

public interface IGradeService {
	
    int deleteByPrimaryKey(Long gradeid);

    int insert(Grade record);

    Grade selectByPrimaryKey(Long gradeid);

    List<Grade> selectAll();

    int updateByPrimaryKey(Grade record);
    
    List<Grade> selectByIdcard(String idcard);
    
    String queryIsPassed(String idcard);
}
