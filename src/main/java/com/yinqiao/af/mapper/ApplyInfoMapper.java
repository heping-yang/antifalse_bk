package com.yinqiao.af.mapper;

import com.yinqiao.af.model.ApplyInfo;

import java.util.List;

public interface ApplyInfoMapper {

    int deleteByPrimaryKey(String bmkid);

    int insertApplyInfo(ApplyInfo record);

    ApplyInfo selectByPrimaryKey(String bmkid);

    List<ApplyInfo> selectAll();

    int updateApplyInfoByPrimaryKey(ApplyInfo record);
    
    String queryApplyCnt(String kstime);
    
    ApplyInfo queryApplyInfoByIdcard(String idcard);
    
    ApplyInfo queryApplyInfoByTelnum(String mobicode);
}