package com.yinqiao.af.service;

import java.util.List;

import com.yinqiao.af.model.Product;

public interface IProductService {

    int deleteByPrimaryKey(String productId);

    int insert(Product record);

    Product selectByPrimaryKey(String productId);

    List<Product> selectAll();

    int updateByPrimaryKey(Product record);
}
