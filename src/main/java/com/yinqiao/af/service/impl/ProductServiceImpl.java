package com.yinqiao.af.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinqiao.af.mapper.ProductMapper;
import com.yinqiao.af.model.Product;
import com.yinqiao.af.service.IProductService;

@Service("productService")
public class ProductServiceImpl implements IProductService {

	@Autowired
	private ProductMapper productMapper;
	
	public int deleteByPrimaryKey(String productId) {
		return productMapper.deleteByPrimaryKey(productId);
	}

	public int insert(Product record) {
		return productMapper.insert(record);
	}

	public Product selectByPrimaryKey(String productId) {
		return productMapper.selectByPrimaryKey(productId);
	}

	public List<Product> selectAll() {
		return productMapper.selectAll();
	}

	public int updateByPrimaryKey(Product record) {
		return productMapper.updateByPrimaryKey(record);
	}

}
