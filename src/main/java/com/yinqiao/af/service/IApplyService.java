package com.yinqiao.af.service;

import java.util.HashMap;
import java.util.List;

import com.yinqiao.af.model.ApplyInfo;
import com.yinqiao.af.model.ExamArea;
import com.yinqiao.af.model.ExamDate;
import com.yinqiao.af.model.KStimeDetail;
import com.yinqiao.af.model.NationInfo;
import com.yinqiao.af.model.OriginInfo;
import com.yinqiao.af.model.RegionInfo;

public interface IApplyService {

	List<String> queryBankType(String zonename);

	List<String> queryBankName(HashMap<String, String> map);

	List<String> selectAllNation();
	
	List<NationInfo> selectValidNation();

	List<KStimeDetail> selectExamDate(String areaid);

	String queryExamAllownums(String detailid);

	String queryApplyCnt(String detailid);

	String queryCheckEnd(String dateid);

	int insertApplyInfo(ApplyInfo record);

	int updateApplyInfoByPrimaryKey(ApplyInfo record);

	ApplyInfo queryApplyInfoByIdcard(String idcard);

	String queryNationId(String nation);

	String queryExamdateId(String examdatetime);

	RegionInfo queryRegByBankName(String bankName);

	String isExamStatus();

	ApplyInfo queryApplyInfoByTelnum(String telnum);

	List<OriginInfo> selectAllOrigin();

	KStimeDetail queryExamdate(String kstimesid);

	List<ExamArea> selectValidExamArea();

	List<OriginInfo> selectOrigin(String ksdqid);
}
