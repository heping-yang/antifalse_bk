package com.yinqiao.af.business;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinqiao.af.model.ApplyInfo;
import com.yinqiao.af.model.RegionInfo;
import com.yinqiao.af.service.IApplyService;

@Service("applyApi")
public class OprApply extends BaseAction{
	
	@Autowired
	private IApplyService applyService;
	
	public String initApply(HttpServletRequest request, HttpServletResponse response){
		JSONObject req = new JSONObject();
		String idcard = request.getParameter("idcard");
		String status = "0";
		ApplyInfo appInfo = applyService.queryApplyInfoByIdcard(idcard);
		if (appInfo != null && !StringUtils.isBlank(appInfo.getBmkid())) {
			status = appInfo.getStatus() + "";
			req.put("applyInfo", appInfo);
			req.put("region", applyService.queryRegByBankName("中国邮政储蓄银行股份有限公司银川市燕庆街营业所"));
		}
		req.put("ksstatus", applyService.isExamStatus());
		req.put("status", status);
		req.put("nation", applyService.selectAllNation());
		return req.toString();
	}
	
	public String queryApplyStatus(HttpServletRequest request, HttpServletResponse response){
		JSONObject req = new JSONObject();
		String idcard = request.getParameter("idcard");
		String status = "0";
		ApplyInfo appInfo = applyService.queryApplyInfoByIdcard(idcard);
		if (appInfo != null && !StringUtils.isBlank(appInfo.getBmkid())) {
			status = appInfo.getStatus() + "";
		}
		req.put("status", status);
		return req.toString();
	}
	
	public String queryBankType(HttpServletRequest request, HttpServletResponse response){
		JSONObject req = new JSONObject();
		String zonename = request.getParameter("zonename");
		String diquname = request.getParameter("diquname");
		String areaid = changeAare("6401");
		req.put("examDate", applyService.selectExamDate("6401"));
		req.put("bankType", applyService.queryBankType("兴庆区"));
		return req.toString();
	}
	
	public String queryBankName(HttpServletRequest request, HttpServletResponse response){
		JSONObject req = new JSONObject();
		String zonename = request.getParameter("zonename");
		String bankType = request.getParameter("bankType");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("zonename", "兴庆区");
		map.put("banktypename", "中信银行");
		req.put("subbankName", applyService.queryBankName(map));
		return req.toString();
	}
	
	public String queryExamAllownums(HttpServletRequest request, HttpServletResponse response){
		JSONObject req = new JSONObject();
		String examdatetime = request.getParameter("examdatetime");
		req.put("allownums", Integer.parseInt(applyService.queryExamAllownums("测试时间"))-Integer.parseInt(applyService.queryApplyCnt("测试时间")));
		String checkEnd = applyService.queryCheckEnd("测试时间");
		if (!StringUtils.isBlank(checkEnd)) {
			req.put("checkEnd", checkEnd.substring(4, 6) + "月" + checkEnd.substring(6, 8) + "日");
		}
		return req.toString();
	}
	
	public String insertApply(HttpServletRequest request, HttpServletResponse response){
		JSONObject req = new JSONObject();
//		try {
			String idcard = request.getParameter("idcard");
			String userName = request.getParameter("userName");
			String telnum = request.getParameter("telnum");
			String diquname = request.getParameter("diquname");
			diquname = "银川市";
			String nation = request.getParameter("nation");
			String bankName = request.getParameter("bankName");
			String examDate = request.getParameter("examDate");
			RegionInfo reginfo = applyService.queryRegByBankName("中国邮政储蓄银行股份有限公司银川市燕庆街营业所");
			ApplyInfo appInfo = applyService.queryApplyInfoByIdcard(idcard);
			if (appInfo != null && !StringUtils.isBlank(appInfo.getBmkid())) {
				appInfo.setBankinfo(reginfo.getBanktype());
				appInfo.setBankcity(reginfo.getBankid());
				appInfo.setBranch(bankName);
				appInfo.setKsdqid(Long.parseLong(changeAare(diquname)));
				appInfo.setKstime(examDate);
				appInfo.setKsdate(applyService.queryExamdateId(examDate));
				applyService.updateApplyInfoByPrimaryKey(appInfo);
			}else {
				appInfo = initApply();
				appInfo.setIdcard(idcard);
				appInfo.setKsname(userName);//姓名
				appInfo.setMobicode(telnum);//电话
				Map<String,String> map = convertIdcard(idcard);
				appInfo.setSex(map.get("sex"));//性别
				appInfo.setNation(applyService.queryNationId("汉族"));//名族
				appInfo.setBirthday(map.get("birthday"));//生日
				appInfo.setBankinfo(reginfo.getBanktype());
				appInfo.setBankcity(reginfo.getBankid());
				appInfo.setBranch(bankName);//银行名称
				appInfo.setKsdqid(Long.parseLong(changeAare(diquname)));//地区id
				appInfo.setKstime(examDate);//考试时间名称
				appInfo.setKsdate(applyService.queryExamdateId(examDate));//考试时间id
				applyService.insertApplyInfo(appInfo);
			}
			req.put("req", "success");
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			req.put("req","failed");
//		}
		return req.toString();
	}
	
	private ApplyInfo initApply(){
		ApplyInfo record = new ApplyInfo();
		record.setBmkid(UUID.randomUUID().toString());
		record.setIdtype("二代身份证");
		record.setStatus(1L);
		record.setKsstatus("1");
		return record;
	}
	
	private String changeAare(String diquname){
		String areaid = "";
		if ("银川市".equals(diquname)) {
			areaid = "6401";
		}else if ("石嘴山市".equals(diquname)) {
			areaid = "6402";
		}else if ("吴忠市".equals(diquname)) {
			areaid = "6403";
		}else if ("固原市".equals(diquname)) {
			areaid = "6404";
		}else if ("中卫市".equals(diquname)) {
			areaid = "6405";
		}
		return areaid;
	}
	
	private Map<String, String> convertIdcard(String idcard){
		Map<String, String> map = new HashMap<String, String>();
		if (!StringUtils.isBlank(idcard) && idcard.length() == 18) {
			int tmpflag = Integer.parseInt(idcard.substring(16,17));
			if (tmpflag%2 == 0) {
				map.put("sex", "女");
			}else {
				map.put("sex", "男");
			}
			map.put("birthday", idcard.substring(6,14));
		}
		return map;
	}
	
	public static void main(String[] args) {
		System.out.println("20160510".substring(4, 6) + "月" + "20160510".substring(6, 8) + "日");
		System.out.println(UUID.randomUUID().toString());
		System.out.println("640381196810233107".substring(16,17));
	}
}
