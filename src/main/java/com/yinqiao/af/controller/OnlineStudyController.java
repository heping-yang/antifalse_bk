package com.yinqiao.af.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/onlinestudy")
public class OnlineStudyController {

	// private static Logger logger =
	// LoggerFactory.getLogger(OnlineStudyController.class);

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		// request.setAttribute("announcements",announcementService.selectAll());
		// request.setAttribute("text","123");
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
}
