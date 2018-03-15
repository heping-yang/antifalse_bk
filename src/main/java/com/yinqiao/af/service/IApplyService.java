package com.yinqiao.af.service;

import java.util.HashMap;
import java.util.List;

import com.yinqiao.af.model.ApplyInfo;
import com.yinqiao.af.model.NationInfo;
import com.yinqiao.af.model.RegionInfo;

public interface IApplyService {
	
    List<String> queryBankType(String zonename);
    
    List<String> queryBankName(HashMap<String, String> map);
    
    List<String> selectAllNation();
    
    List<String> selectExamDate(String areaid);
    
    String queryExamAllownums(String examdatetime);
    
    String queryApplyCnt(String examdatetime);
    
    String queryCheckEnd(String examdatetime);
    
    int insertApplyInfo(ApplyInfo record);
    
    int updateApplyInfoByPrimaryKey(ApplyInfo record);
    
    ApplyInfo queryApplyInfoByIdcard(String idcard);
    
    String queryNationId(String nation);
    
    String queryExamdateId(String examdatetime);
    
    RegionInfo queryRegByBankName(String bankName);
    
    String isExamStatus();
    
    ApplyInfo queryApplyInfoByTelnum(String telnum);
}
