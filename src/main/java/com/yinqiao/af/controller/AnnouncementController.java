package com.yinqiao.af.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yinqiao.af.service.IAnnouncementService;

@Controller
@RequestMapping(value = "/announcement")
public class AnnouncementController {
	
	@Autowired
	private IAnnouncementService announcementService;
	
	private static Logger logger = LoggerFactory
			.getLogger(AnnouncementController.class);

	@RequestMapping(value = "/notice", method = RequestMethod.GET)
	public String notice(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		request.setAttribute("announcements",announcementService.selectAll());
		request.setAttribute("text","123");
		return "notice";
	}
	
	@RequestMapping(value = "/noticeShow", method = RequestMethod.GET)
	public String noticeShow(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		String msgid = request.getParameter("id");
		if (StringUtils.isBlank(msgid)) {
			return "notice_show";
		}else{
			request.setAttribute("announcement", announcementService.selectByPrimaryKey(msgid));
			return "notice_one";
		}
		
	}
}
