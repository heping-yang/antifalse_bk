package com.yinqiao.af.service;

import java.util.List;

import com.yinqiao.af.model.ExamHistory;
import com.yinqiao.af.model.UserExamHistory;

public interface IExamHistoryService {

    int deleteByPrimaryKey(String hId);

    int insert(ExamHistory record);

    ExamHistory selectByPrimaryKey(String hId);

    List<ExamHistory> selectAll(String telnum);

    int updateByPrimaryKey(ExamHistory record);
    
    String isExist(String hId);
}
