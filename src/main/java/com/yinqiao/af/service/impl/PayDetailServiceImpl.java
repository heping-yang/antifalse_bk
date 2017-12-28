package com.yinqiao.af.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinqiao.af.model.PayDetail;
import com.yinqiao.af.service.IPayDetailService;

@Service("payDetailService")
public class PayDetailServiceImpl implements IPayDetailService{

	@Autowired
//	private PayDetailMapper payDetailMapper;
	
	public int insert(PayDetail record) {
//		return payDetailMapper.insert(record);
		return 0;
	}

	public List<PayDetail> selectAll() {
//		return payDetailMapper.selectAll();
		return null;
	}

	public int update(PayDetail record) {
//		return payDetailMapper.update(record);
		return 0;
	}

	public Map<String, Object> queryPayDetailById(String orderid) {
//		return payDetailMapper.selectPayDetailById(orderid);
		return null;
	}
	
}
