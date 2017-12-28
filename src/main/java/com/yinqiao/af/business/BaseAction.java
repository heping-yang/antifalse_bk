package com.yinqiao.af.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;

import com.yinqiao.af.model.Retinfo;
import com.yinqiao.af.utils.StringUtil;

public abstract class BaseAction {
	
	private static Logger logger = LoggerFactory.getLogger(BaseAction.class);

	@Autowired
	protected SessionRepository<Session> sessionRepository;
	
	public Session getAuthSession(String sid){
		return this.sessionRepository.getSession(sid);
	}
	
	public String checkPassport(String sid, Retinfo retinfo){
		String servNumber = "";
		Session authSession= getAuthSession(sid);
		logger.info("authSession is {}", authSession);
		if(authSession!=null&&(Boolean)authSession.getAttribute("authenticated")){
			logger.info("passport is {}", authSession.getAttribute("passport"));
			logger.info("provider is {}", authSession.getAttribute("provider"));
			if(!StringUtil.checkNull("")){
//				servNumber = passport.getServNumber();
			}else{
				retinfo.setRetCode("999");
				retinfo.setRetMsg("没有绑定");
			}
			
		}else{
			retinfo.setRetCode("403");
			retinfo.setRetMsg("未登录");
		}
		
		return servNumber;
	}

}
