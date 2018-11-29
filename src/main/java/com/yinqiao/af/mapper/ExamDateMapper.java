package com.yinqiao.af.mapper;

import com.yinqiao.af.model.ExamDate;

import java.util.List;

public interface ExamDateMapper {

    int deleteByPrimaryKey(String dateid);

    int insert(ExamDate record);

    ExamDate selectByPrimaryKey(String dateid);

    List<ExamDate> selectAll();

    int updateByPrimaryKey(ExamDate record);
    
    List<String> selectExamDate(String areaid);
    
    String queryExamAllownums(String examdatetime);
    
    String queryCheckEnd(String dateid);
    
    String queryExamdateId(String examdatetime);
    
    String isExamStatus();
}