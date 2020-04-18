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
import com.yinqiao.af.model.ExamArea;
import com.yinqiao.af.model.KStimeDetail;
import com.yinqiao.af.model.NationInfo;
import com.yinqiao.af.model.OriginInfo;
import com.yinqiao.af.model.RegionInfo;
import com.yinqiao.af.service.IApplyService;
import com.yinqiao.af.service.IExamHistoryService;
import com.yinqiao.af.utils.DataUtil;

import net.sf.json.JSONObject;

@Service("applyApi")
public class OprApply extends BaseAction {

	private static Logger Logger = LoggerFactory.getLogger(OprApply.class);

	@Autowired
	private IApplyService applyService;

	@Autowired
	private IExamHistoryService examHistoryService;

	public String initApply(HttpServletRequest request, HttpServletResponse response) {
		JSONObject req = new JSONObject();
		String idcard = request.getParameter("idcard");
		String telnum = request.getParameter("telnum");
		String status = "0";
		List<NationInfo> nations = applyService.selectValidNation();
		List<ExamArea> examAreas = applyService.selectValidExamArea();
		ApplyInfo appInfo = applyService.queryApplyInfoByIdcard(idcard);
		if (appInfo != null && !StringUtils.isBlank(appInfo.getBmkid())) {
			status = appInfo.getStatus() + "";
			KStimeDetail examDate = applyService.queryExamdate(appInfo.getKstimesid());
			if (examDate != null) {
				appInfo.setKstime(examDate.getDetailtime());
			}
			req.put("applyInfo", appInfo);
			req.put("region", applyService.queryRegByBankName(appInfo.getBranch()));
			String checkEnd = applyService.queryCheckEnd(appInfo.getKsdate());
			if (!StringUtils.isBlank(checkEnd)) {
				req.put("checkEnd", checkEnd.substring(5, 7) + "月" + checkEnd.substring(8, 10) + "日");
			}
		} else {
			appInfo = initApply();
		}
		req.put("applyInfo", appInfo);
		req.put("ksstatus", applyService.isExamStatus());
		req.put("status", status);
		req.put("nation", nations);
		req.put("ksdq", examAreas);

		int count = examHistoryService.countByScore(telnum, 80);
		req.put("canApply", count >= 3);

		return req.toString();
	}

	public String queryOrigin(HttpServletRequest request, HttpServletResponse response) {
		String ksdqid = request.getParameter("ksdqid");
		List<OriginInfo> origins = applyService.selectOrigin(ksdqid);
		JSONObject data = new JSONObject();
		data.put("origins", origins);
		data.put("examDate", applyService.selectExamDate(ksdqid));
		return data.toString();
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
		String detailid = request.getParameter("detailid");
		String dateid = request.getParameter("dateid");
		String allownums = request.getParameter("allownums");
		// String allownums = applyService.queryExamAllownums(detailid);
		String applycnt = applyService.queryApplyCnt(detailid);

		req.put("allownums", DataUtil.toInteger(allownums, 0) - DataUtil.toInteger(applycnt, 0));
		String checkEnd = applyService.queryCheckEnd(dateid);
		if (!StringUtils.isBlank(checkEnd)) {
			req.put("checkEnd", checkEnd.substring(5, 7) + "月" + checkEnd.substring(8, 10) + "日");
		}
		return req.toString();
	}

	public String insertApply(HttpServletRequest request, HttpServletResponse response) {
		JSONObject req = new JSONObject();
		try {
			String idcard = request.getParameter("idcard");
			String userName = request.getParameter("userName");
			String telnum = request.getParameter("telnum");
			String ksdqid = request.getParameter("ksdqid");
			// String nation = request.getParameter("nation");
			// String bankName = request.getParameter("bankName");
			String dateid = request.getParameter("dateid");
			// String kssource = request.getParameter("kssource");
			String kstimesid = request.getParameter("kstimesid");
			// RegionInfo reginfo = applyService.queryRegByBankName(bankName);
			ApplyInfo appInfo = applyService.queryApplyInfoByIdcard(idcard);

			if (appInfo != null && !StringUtils.isBlank(appInfo.getBmkid())) {
				// appInfo.setBankinfo(reginfo.getBanktype());
				// appInfo.setBankcity(reginfo.getBankid());
				// appInfo.setBranch(bankName);
				// appInfo.setNation(nation);
				appInfo.setKsdqid(Long.parseLong(ksdqid));
				appInfo.setKstime(dateid);
				appInfo.setKsdate(dateid);
				appInfo.setKstimesid(kstimesid);
				appInfo.setStatus(1L);
				// appInfo.setKssource(kssource);
				applyService.updateApplyInfoByPrimaryKey(appInfo);
			} else {
				appInfo = initApply();
				appInfo.setIdcard(idcard);
				appInfo.setKsname(userName);// 姓名
				appInfo.setMobicode(telnum);// 电话
				Map<String, String> map = convertIdcard(idcard);
				appInfo.setSex(map.get("sex"));// 性别
				// appInfo.setNation(nation);// 名族
				appInfo.setBirthday(map.get("birthday"));// 生日
				// appInfo.setBankinfo(reginfo.getBanktype());
				// appInfo.setBankcity(reginfo.getBankid());
				// appInfo.setBranch(bankName);// 银行名称
				appInfo.setKsdqid(Long.parseLong(ksdqid));// 地区id
				appInfo.setKstime(dateid);// 考试时间名称
				appInfo.setKsdate(dateid);// 考试时间id
				appInfo.setKstimesid(kstimesid);
				appInfo.setBmtime(DataUtil.sdf.format(new Date()));
				// appInfo.setKssource(kssource);

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
