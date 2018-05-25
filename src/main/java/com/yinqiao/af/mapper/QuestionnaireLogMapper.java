package com.yinqiao.af.mapper;

import com.yinqiao.af.model.QuestionnaireLog;
import java.util.List;

public interface QuestionnaireLogMapper {

    int deleteByPrimaryKey(String logsid);

    int insert(QuestionnaireLog record);

    QuestionnaireLog selectByPrimaryKey(String logsid);

    List<QuestionnaireLog> selectAll();

    int updateByPrimaryKey(QuestionnaireLog record);
}