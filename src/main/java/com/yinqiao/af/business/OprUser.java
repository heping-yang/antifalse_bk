package com.yinqiao.af.business;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinqiao.af.model.Grade;
import com.yinqiao.af.model.User;
import com.yinqiao.af.service.IEnrollService;
import com.yinqiao.af.service.IGradeService;
import com.yinqiao.af.service.IOrderInfoService;
import com.yinqiao.af.service.IProductService;
import com.yinqiao.af.service.IUserService;
import com.yinqiao.af.utils.JSDKUtil;

@Service("userApi")
public class OprUser extends BaseAction{
	
	private static Logger logger = LoggerFactory.getLogger(OprUser.class);
	
	@Autowired
	private IGradeService gradeService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private IOrderInfoService orderInfoService;
	
	@Autowired
	private IEnrollService enrollService;
	
	public String queryGrade(HttpServletRequest request, HttpServletResponse response){
		JSONObject req = new JSONObject();
		String idcard = request.getParameter("idcard");
		List<Grade> grades = gradeService.selectByIdcard(idcard);
		System.out.println(JSONArray.fromObject(grades).toString());
		if (null != grades && grades.size()>0) {
			grades.get(0).setKeepTime(getKeepTime(grades.get(0).getKstime()));
			req.put("grade", JSONObject.fromObject(grades.get(0)).toString());
		}else {
			req.put("grade", "无记录");
		}
		return req.toString();
	}
	
	private String getKeepTime(String kstime){
		String keepTime = "";
		if(!StringUtils.isBlank(kstime)){
			keepTime = (Integer.parseInt(kstime.substring(0,4))+2) + kstime.substring(4);
		}
		return keepTime;
	}
	
	public String login(HttpServletRequest request, HttpServletResponse response){
		JSONObject req = new JSONObject();
		String telnum = request.getParameter("telnum");
		String password = request.getParameter("password");
		User user = userService.selectByPrimaryKey(telnum);
		if (null != user && JSDKUtil.encodeByMD5(password).equals(user.getPassword())) {
			req.put("login", "success");
			req.put("user", JSONObject.fromObject(userCheck(user)).toString());
		}else {
			req.put("login", "fail");
		}
		return req.toString();
	}
	
	public String  relogin(HttpServletRequest request, HttpServletResponse response) {
		JSONObject req = new JSONObject();
		String telnum = request.getParameter("telnum");
		User user = userService.selectByPrimaryKey(telnum);
		req.put("user", JSONObject.fromObject(userCheck(user)).toString());
		return req.toString();
	}
	
	public String register(HttpServletRequest request, HttpServletResponse response){
		JSONObject req = new JSONObject();
		String telnum = request.getParameter("telnum");
		String password = request.getParameter("password");
		String openid = request.getParameter("openid");
		String username = request.getParameter("username");
		String idcard = request.getParameter("idcard");
		User user = getNewUser(telnum,password,openid,username,idcard);
		if (userService.insert(user) > 0) {
			req.put("register", "success");
			req.put("user", JSONObject.fromObject(userCheck(userService.selectByPrimaryKey(telnum))).toString());
		}else {
			req.put("register", "fail");
		}
		return req.toString();
	}
	
	public String userIsExist(HttpServletRequest request, HttpServletResponse response){
		JSONObject req = new JSONObject();
		String telnum = request.getParameter("telnum");
		User user = userService.selectByPrimaryKey(telnum);
		if (null != user) {
			req.put("isExist", "1");
		}else {
			req.put("isExist", "0");
		}
		return req.toString();
	}
	
	private User userCheck(User user){
		if("1".equals(userService.queryUserIsEffective(user.getIdcard()))){
			if ("2".equals(user.getUserstatus()) && "1".equals(enrollService.queryIsEnrolled(user.getIdcard()))) {
				user.setUserstatus("3");
				userService.updateByPrimaryKey(user);
			}
		}else {
			user.setUserstatus("1");
			userService.updateByPrimaryKey(user);
		}
		return user;
	}
	
	private User getNewUser(String telnum,String password,String openid,String username,String idcard){
		User user = new User();
		user.setCreatetime(new Date());
		user.setEffend(new Date());
		user.setEffstart(new Date());
		user.setIdcard(idcard);
		user.setOpenid(openid);
		user.setPassword(JSDKUtil.encodeByMD5(password));
		user.setTelnum(telnum);
		user.setUsername(username);
		//萌新会员
		user.setUserstatus("1");
		return user;
	}
	
	public String respwd(HttpServletRequest request, HttpServletResponse response){
		JSONObject req = new JSONObject();
		String telnum = request.getParameter("telnum");
		String password = request.getParameter("password");
		User user = userService.selectByPrimaryKey(telnum);
		if (null != user) {
			user.setPassword(JSDKUtil.encodeByMD5(password));
			userService.updateByPrimaryKey(user);
			req.put("respwd", "success");
		}else {
			req.put("respwd", "upfail");
		}
		return req.toString();
	}
	
	public String queryProduct(HttpServletRequest request, HttpServletResponse response){
		JSONObject req = new JSONObject();
		String idcard = request.getParameter("idcard");
		String userstatus = request.getParameter("userstatus");
		List<Grade> grades = gradeService.selectByIdcard(idcard);
		System.out.println(JSONArray.fromObject(grades).toString());
		
		if (!StringUtils.isBlank(userstatus) && "1".equals(userstatus)) {
			if (null != grades && grades.size()>0) {
				if ("通过".equals(grades.get(0).getGrade())) {
					req.put("product", JSONObject.fromObject(productService.selectByPrimaryKey("Y01")).toString());
					req.put("count", orderInfoService.queryCntByProductId("Y01"));
				}else {
					req.put("product", JSONObject.fromObject(productService.selectByPrimaryKey("M01")).toString());
					req.put("count", orderInfoService.queryCntByProductId("M01"));
				}
			}
		}
		return req.toString();
	}
	
	public static void main(String[] args) {

	}
}
