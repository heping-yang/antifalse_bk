package com.yinqiao.af.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/announcement")
public class AnnouncementController {
	private static Logger logger = LoggerFactory
			.getLogger(AnnouncementController.class);

	@RequestMapping(value = "/notice", method = RequestMethod.GET)
	public ModelAndView notice(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		ModelAndView mav = new ModelAndView("notice");
		return mav;
	}
	
	@RequestMapping(value = "/noticeShow", method = RequestMethod.GET)
	public ModelAndView noticeShow(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		ModelAndView mav = new ModelAndView("notice_show");
		return mav;
	}
}
