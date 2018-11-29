package com.yinqiao.af.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yinqiao.af.model.KStimeDetail;

public interface KStimeDetailMapper {
	List<KStimeDetail> selectValidByArea(@Param("areaid") String areaid);

	String queryExamAllownums(@Param("detailid") String detailid);

	KStimeDetail selectByPrimaryKey(@Param("detailid") String detailid);
}