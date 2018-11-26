package com.yinqiao.af.business;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinqiao.af.model.ApplyInfo;
import com.yinqiao.af.model.ExamDate;
import com.yinqiao.af.model.NationInfo;
import com.yinqiao.af.model.OriginInfo;
import com.yinqiao.af.model.RegionInfo;
import com.yinqiao.af.service.IApplyService;
import com.yinqiao.af.utils.DataUtil;

import net.sf.json.JSONObject;

@Service("applyApi")
public class OprApply extends BaseAction {

	private static Logger Logger = LoggerFactory.getLogger(OprApply.class);

	@Autowired
	private IApplyService applyService;

	public String initApply(HttpServletRequest request, HttpServletResponse response) {
		JSONObject req = new JSONObject();
		String idcard = request.getParameter("idcard");
		String status = "0";
		List<OriginInfo> origins = applyService.selectAllOrigin();
		List<NationInfo> nations = applyService.selectValidAll();
		ApplyInfo appInfo = applyService.queryApplyInfoByIdcard(idcard);
		if (appInfo != null && !StringUtils.isBlank(appInfo.getBmkid())) {
			status = appInfo.getStatus() + "";
			if (origins != null && origins.size() > 0 && !DataUtil.isEmpty(appInfo.getKssource())) {
				for (int i = 0; i < origins.size(); i++) {
					OriginInfo oi = origins.get(i);
					if (oi.getOriginid() != null && oi.getOriginid().toString().equals(appInfo.getKssource())) {
						appInfo.setKssource(oi.getOriginname());
						break;
					}
				}
			}
			if (nations != null && !StringUtils.isBlank(appInfo.getNation())) {
				for(int i=0;i<nations.size();i++){
					NationInfo ni = nations.get(i);
					if(ni.getNationid()!=null && ni.getNationid().equals(appInfo.getNation())){
						appInfo.setNation(ni.getNationname());
						break;
					}
				}
			}
			ExamDate examDate = applyService.queryExamdate(appInfo.getKsdate());
			if(examDate!=null){
				appInfo.setKstime(examDate.getExamdatetime());
			}

			req.put("applyInfo", appInfo);
			req.put("region", applyService.queryRegByBankName(appInfo.getBranch()));
			String checkEnd = applyService.queryCheckEnd(appInfo.getKstime());
			if (!StringUtils.isBlank(checkEnd)) {
				req.put("checkEnd", checkEnd.substring(4, 6) + "月" + checkEnd.substring(6, 8) + "日");
			}
		}else{
			appInfo = initApply();
		}
		req.put("applyInfo", appInfo);
		req.put("ksstatus", applyService.isExamStatus());
		req.put("status", status);
		req.put("nation", nations);
		req.put("kssource", origins);

		return req.toString();
	}

	public String queryApplyStatus(HttpServletRequest request, HttpServletResponse response) {
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

	public String queryBankType(HttpServletRequest request, HttpServletResponse response) {
		JSONObject req = new JSONObject();
		String zonename = request.getParameter("zonename");
		String diquname = request.getParameter("diquname");
		String areaid = changeAare(diquname);
		req.put("examDate", applyService.selectExamDate(areaid));
		req.put("bankType", applyService.queryBankType(zonename));
		return req.toString();
	}

	public String queryBankName(HttpServletRequest request, HttpServletResponse response) {
		JSONObject req = new JSONObject();
		String zonename = request.getParameter("zonename");
		String bankType = request.getParameter("bankType");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("zonename", zonename);
		map.put("banktypename", bankType);
		req.put("subbankName", applyService.queryBankName(map));
		return req.toString();
	}

	public String queryExamAllownums(HttpServletRequest request, HttpServletResponse response) {
		JSONObject req = new JSONObject();
		String examdatetime = request.getParameter("examdatetime");
		String allonums = applyService.queryExamAllownums(examdatetime);
		String applycnt = applyService.queryApplyCnt(examdatetime);

		req.put("allownums", DataUtil.toInteger(allonums, 0) - DataUtil.toInteger(applycnt, 0));
		String checkEnd = applyService.queryCheckEnd(examdatetime);
		if (!StringUtils.isBlank(checkEnd)) {
			req.put("checkEnd", checkEnd.substring(4, 6) + "月" + checkEnd.substring(6, 8) + "日");
		}
		return req.toString();
	}

	public String insertApply(HttpServletRequest request, HttpServletResponse response) {
		JSONObject req = new JSONObject();
		try {
			String idcard = request.getParameter("idcard");
			String userName = request.getParameter("userName");
			String telnum = request.getParameter("telnum");
			String diquname = request.getParameter("diquname");
			String nation = request.getParameter("nation");
			String bankName = request.getParameter("bankName");
			String examDate = request.getParameter("examDate");
			String kssource = request.getParameter("kssource");
			RegionInfo reginfo = applyService.queryRegByBankName(bankName);
			ApplyInfo appInfo = applyService.queryApplyInfoByIdcard(idcard);

			String examdateId = applyService.queryExamdateId(examDate);
			if (appInfo != null && !StringUtils.isBlank(appInfo.getBmkid())) {
				appInfo.setBankinfo(reginfo.getBanktype());
				appInfo.setBankcity(reginfo.getBankid());
				appInfo.setBranch(bankName);
				appInfo.setNation(nation);
				appInfo.setKsdqid(Long.parseLong(changeAare(diquname)));
				appInfo.setKstime(examdateId);
				appInfo.setKsdate(examdateId);
				appInfo.setStatus(1L);
				appInfo.setKssource(kssource);
				applyService.updateApplyInfoByPrimaryKey(appInfo);
			} else {
				appInfo = initApply();
				appInfo.setIdcard(idcard);
				appInfo.setKsname(userName);// 姓名
				appInfo.setMobicode(telnum);// 电话
				Map<String, String> map = convertIdcard(idcard);
				appInfo.setSex(map.get("sex"));// 性别
				appInfo.setNation(nation);// 名族
				appInfo.setBirthday(map.get("birthday"));// 生日
				appInfo.setBankinfo(reginfo.getBanktype());
				appInfo.setBankcity(reginfo.getBankid());
				appInfo.setBranch(bankName);// 银行名称
				appInfo.setKsdqid(Long.parseLong(changeAare(diquname)));// 地区id
				appInfo.setKstime(examdateId);// 考试时间名称
				appInfo.setKsdate(examdateId);// 考试时间id
				appInfo.setBmtime(DataUtil.sdf.format(new Date()));
				appInfo.setKssource(kssource);
				applyService.insertApplyInfo(appInfo);
			}
			req.put("req", "success");
		} catch (Exception e) {
			Logger.info(e.getMessage());
			req.put("req", "failed");
		}
		return req.toString();
	}

	private ApplyInfo initApply() {
		ApplyInfo record = new ApplyInfo();
		record.setBmkid(UUID.randomUUID().toString());
		record.setIdtype("二代身份证");
		record.setStatus(1L);
		record.setKsstatus("1");
		record.setClfees("150");
		record.setKsfees("150");
		record.setMoneys("310");
		record.setNetpsword("000000");
		return record;
	}

	private String changeAare(String diquname) {
		String areaid = "";
		if ("银川市".equals(diquname)) {
			areaid = "6401";
		} else if ("石嘴山市".equals(diquname)) {
			areaid = "6402";
		} else if ("吴忠市".equals(diquname)) {
			areaid = "6403";
		} else if ("固原市".equals(diquname)) {
			areaid = "6404";
		} else if ("中卫市".equals(diquname)) {
			areaid = "6405";
		}
		return areaid;
	}

	private Map<String, String> convertIdcard(String idcard) {
		Map<String, String> map = new HashMap<String, String>();
		if (!StringUtils.isBlank(idcard) && idcard.length() == 18) {
			int tmpflag = Integer.parseInt(idcard.substring(16, 17));
			if (tmpflag % 2 == 0) {
				map.put("sex", "女");
			} else {
				map.put("sex", "男");
			}
			map.put("birthday", idcard.substring(6, 14));
		}
		return map;
	}

	public static void main(String[] args) {
		System.out.println("20160510".substring(4, 6) + "月" + "20160510".substring(6, 8) + "日");
		System.out.println(UUID.randomUUID().toString());
		System.out.println("640381196810233107".substring(16, 17));
	}
}
