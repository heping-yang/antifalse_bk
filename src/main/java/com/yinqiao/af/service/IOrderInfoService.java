package com.yinqiao.af.service;

import java.util.List;

import com.yinqiao.af.model.OrderInfo;

public interface IOrderInfoService {

    int deleteByPrimaryKey(String orderId);

    int insert(OrderInfo record);

    OrderInfo selectByPrimaryKey(String orderId);

    List<OrderInfo> selectAll();

    int updateByPrimaryKey(OrderInfo record);
    
    String queryCntByProductId(String productId);
}
