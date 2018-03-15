package com.yinqiao.af.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinqiao.af.mapper.ApplyInfoMapper;
import com.yinqiao.af.mapper.ExamDateMapper;
import com.yinqiao.af.mapper.NationInfoMapper;
import com.yinqiao.af.mapper.RegionInfoMapper;
import com.yinqiao.af.model.ApplyInfo;
import com.yinqiao.af.model.NationInfo;
import com.yinqiao.af.model.RegionInfo;
import com.yinqiao.af.service.IApplyService;

@Service("applyService")
public class ApplyServiceImpl implements IApplyService {

	@Autowired
	private RegionInfoMapper regionInfoMapper;
	
	@Autowired
	private NationInfoMapper nationInfoMapper;
	
	@Autowired
	private ExamDateMapper examDateMapper;
	
	@Autowired ApplyInfoMapper applyInfoMapper;

	public List<String> queryBankType(String zonename) {
		return regionInfoMapper.queryBankType(zonename);
	}

	public List<String> queryBankName(HashMap<String, String> map) {
		return regionInfoMapper.queryBankName(map);
	}

	public List<String> selectAllNation() {
		return nationInfoMapper.selectAllNation();
	}

	public List<String> selectExamDate(String areaid) {
		return examDateMapper.selectExamDate(areaid);
	}

	public String queryExamAllownums(String examdatetime) {
		return examDateMapper.queryExamAllownums(examdatetime);
	}

	public String queryApplyCnt(String examdatetime) {
		return applyInfoMapper.queryApplyCnt(examdatetime);
	}

	public String queryCheckEnd(String examdatetime) {
		return examDateMapper.queryCheckEnd(examdatetime);
	}

	public int insertApplyInfo(ApplyInfo record) {
		return applyInfoMapper.insertApplyInfo(record);
	}

	public ApplyInfo queryApplyInfoByIdcard(String idcard) {
		return applyInfoMapper.queryApplyInfoByIdcard(idcard);
	}

	public int updateApplyInfoByPrimaryKey(ApplyInfo record) {
		return applyInfoMapper.updateApplyInfoByPrimaryKey(record);
	}

	public String queryNationId(String nation) {
		return nationInfoMapper.queryNationId(nation);
	}

	public String queryExamdateId(String examdatetime) {
		return examDateMapper.queryExamdateId(examdatetime);
	}

	public RegionInfo queryRegByBankName(String bankName) {
		return regionInfoMapper.queryRegByBankName(bankName);
	}

	public String isExamStatus() {
		return examDateMapper.isExamStatus();
	}

	public ApplyInfo queryApplyInfoByTelnum(String telnum) {
		return applyInfoMapper.queryApplyInfoByTelnum(telnum);
	}
}
