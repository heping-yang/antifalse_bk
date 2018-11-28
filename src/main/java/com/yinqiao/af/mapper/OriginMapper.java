package com.yinqiao.af.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yinqiao.af.model.OriginInfo;

public interface OriginMapper {
	List<OriginInfo> selectValidAll();

	List<OriginInfo> selectByParent(@Param("parent") String parent);
}