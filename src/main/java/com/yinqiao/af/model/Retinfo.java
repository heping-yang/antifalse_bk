package com.yinqiao.af.model;

import java.io.Serializable;

public class Retinfo implements Serializable{
	
	private static final long serialVersionUID = -4026512820863325958L;
	private String retCode;//返回码
	private String retType;//返回类型
	private String retMsg;//返回消息
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getRetType() {
		return retType;
	}
	public void setRetType(String retType) {
		this.retType = retType;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

}