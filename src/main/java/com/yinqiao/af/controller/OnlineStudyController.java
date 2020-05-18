package com.yinqiao.af.controller;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yinqiao.af.model.OnlineStudy;
import com.yinqiao.af.model.User;
import com.yinqiao.af.service.IOnlineStudyService;
import com.yinqiao.af.service.IUserService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/onlinestudy")
public class OnlineStudyController {

	private static Logger log = LoggerFactory.getLogger(OnlineStudyController.class);

	@Autowired
	private IOnlineStudyService onlineStudyService;

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/list/{telnum}", method = RequestMethod.GET)
	public String list(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			@PathVariable String telnum) {
		request.getSession().setAttribute("telnum", telnum);
		return "onlinestudy/list";
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String notice(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		// request.setAttribute("announcements",announcementService.selectAll());
		// request.setAttribute("text","123");
		return "onlinestudy/show";
	}

	@RequestMapping(value = "/save", method = RequestMethod.GET)
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		// request.setAttribute("announcements",announcementService.selectAll());
		// request.setAttribute("text","123");
		// return "questionnaire/questionnaire";
	}

	@RequestMapping(value = "start/{chapter}", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject start(HttpServletRequest request, @PathVariable String chapter, String lid, String lname,
			Integer period) {
		JSONObject ret = new JSONObject();
		ret.put("ret_code", "0");
		String telnum = (String) request.getSession(true).getAttribute("telnum");
		if (telnum == null || telnum.trim().length() == 0) {
			ret.put("ret_code", "1");
			ret.put("ret_msg", "学习失败");
			log.info("无法读取openid，章节[" + chapter + "]学习失败");
			return ret;
		}
		log.debug("开始学习 章节[" + chapter + "]，telnum=" + telnum);

		// 根据telnum查询openid
		String openid = "";
		User user = userService.selectByPrimaryKey(telnum);
		if (user != null) {
			openid = user.getOpenid();
		}

		OnlineStudy study = new OnlineStudy();
		study.setId(UUID.randomUUID().toString());
		study.setTelnum(telnum);
		study.setOpenid(openid);
		study.setLessonId(lid);
		study.setLessonName(lname);
		study.setLessonPeriod(period);
		study.setScore(0);
		study.setStartTime(new Date());
		study.setFinishTime(study.getStartTime());
		onlineStudyService.insert(study);

		ret.put("token", study.getId());
		return ret;
	}

	@RequestMapping(value = "finish/{chapter}", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject finish(HttpServletRequest request, @PathVariable String chapter, String token) {
		JSONObject ret = new JSONObject();
		ret.put("ret_code", "0");
		String telnum = (String) request.getSession(true).getAttribute("telnum");
		if (telnum == null || telnum.trim().length() == 0) {
			ret.put("ret_code", "1");
			ret.put("ret_msg", "学习失败");
			log.info("无法读取openid，章节[" + chapter + "]学习失败");
			return ret;
		}
		log.debug("结束学习 章节[" + chapter + "]，telnum=" + telnum);
		// 更新学习时间
		updateStudy(token);
		return ret;
	}

	@RequestMapping(value = "heartbeat/{chapter}", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject heartbeat(HttpServletRequest request, @PathVariable String chapter, String token) {
		JSONObject ret = new JSONObject();
		ret.put("ret_code", "0");
		String telnum = (String) request.getSession(true).getAttribute("telnum");
		if (telnum == null || telnum.trim().length() == 0) {
			ret.put("ret_code", "1");
			ret.put("ret_msg", "学习失败");
			log.info("无法读取openid，章节[" + chapter + "]学习失败");
			return ret;
		}
		log.debug("心跳包 章节[" + chapter + "]，telnum=" + telnum);
		// 更新学习时间
		updateStudy(token);
		return ret;
	}

	private void updateStudy(String token) {
		OnlineStudy study = onlineStudyService.select(token);
		if (study != null) {
			
			study.setFinishTime(new Date());
			long l = study.getFinishTime().getTime() - study.getStartTime().getTime();
			l = l / (60 * 1000); // 分钟
			
			study.setScore((int)l);
			study.setTimes((int)l);
			onlineStudyService.update(study);
		}
	}

}
