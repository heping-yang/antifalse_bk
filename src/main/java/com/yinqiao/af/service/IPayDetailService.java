package com.yinqiao.af.service;

import java.util.List;
import java.util.Map;

import com.yinqiao.af.model.PayDetail;

public interface IPayDetailService {

	/**
	 * 新增支付信息
	 * @param record
	 * @return
	 */
    int insert(PayDetail record);

    /**
     * 查询支付列表
     * @return
     */
    List<PayDetail> selectAll();
    
    /**
     * 更新支付信息
     * @param record
     * @return
     */
    int update(PayDetail record);

    /**
     * 查询支付信息
     * @param orderid
     * @return
     */
	Map<String, Object> queryPayDetailById(String orderid);
}
