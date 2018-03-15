package com.yinqiao.af.mapper;

import com.yinqiao.af.model.RegionInfo;

import java.util.HashMap;
import java.util.List;

public interface RegionInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AF_REGION_INFO
     *
     * @mbg.generated Tue Mar 13 17:38:59 CST 2018
     */
    int insert(RegionInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AF_REGION_INFO
     *
     * @mbg.generated Tue Mar 13 17:38:59 CST 2018
     */
    List<RegionInfo> selectAll();
    
    List<String> queryBankType(String zonename);
    
    List<String> queryBankName(HashMap<String, String> map);
    
    RegionInfo queryRegByBankName(String bankName);
}