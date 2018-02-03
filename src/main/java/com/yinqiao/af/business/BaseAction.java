package com.yinqiao.af.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yinqiao.af.model.Retinfo;

public abstract class BaseAction {
	
	private static Logger logger = LoggerFactory.getLogger(BaseAction.class);
	
	public String checkPassport(String sid, Retinfo retinfo){
		String servNumber = "";
		return servNumber;
	}
}
