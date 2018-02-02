package com.yinqiao.af.weixin.msg;

public abstract class BaseTemplateMessage {
	
	private String ToUserName;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	
	public abstract String toJSONString();
	

}
