package com.yinqiao.af.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinqiao.af.mapper.OrderInfoMapper;
import com.yinqiao.af.model.OrderInfo;
import com.yinqiao.af.service.IOrderInfoService;

@Service("orderInfoService")
public class OrderInfoServiceImpl implements IOrderInfoService {

	@Autowired
	private OrderInfoMapper orderInfoMapper;
	
	public int deleteByPrimaryKey(String orderId) {
		return orderInfoMapper.deleteByPrimaryKey(orderId);
	}

	public int insert(OrderInfo record) {
		return orderInfoMapper.insert(record);
	}

	public OrderInfo selectByPrimaryKey(String orderId) {
		return orderInfoMapper.selectByPrimaryKey(orderId);
	}

	public List<OrderInfo> selectAll() {
		return orderInfoMapper.selectAll();
	}

	public int updateByPrimaryKey(OrderInfo record) {
		return orderInfoMapper.updateByPrimaryKey(record);
	}

	public String queryCntByProductId(String productId) {
		return orderInfoMapper.queryCntByProductId(productId);
	}

}
