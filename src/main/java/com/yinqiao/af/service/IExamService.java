package com.yinqiao.af.service;

import java.util.List;

import com.yinqiao.af.model.ExamList;

public interface IExamService {

    int deleteByPrimaryKey(String examId);

    int insert(ExamList record);

    ExamList selectByPrimaryKey(String examId);

    List<ExamList> selectAll();

    int updateByPrimaryKey(ExamList record);
    
    String queryExamNameById(String examId);

	List<ExamList> selectByUser(String telnum);
}
