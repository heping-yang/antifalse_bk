package com.yinqiao.af.weixin.msg;

/**
 * {{first.DATA}}
本机号码：{{tel.DATA}}
已用流量：{{used1.DATA}}
总流量：{{Surplus1.DATA}}
{{remark.DATA}} 
 * */

public class FlowWarningTemplateMessage extends BaseTemplateMessage{

	private String url;
	
	private String data = 
			"\"first\":{\"value\":\"FIRST\",\"color\":\"#000000\"}," +
			"\"tel\":{\"value\":\"TEL\",\"color\":\"#173177\"}," +
			"\"used1\":{\"value\":\"USED1\",\"color\":\"#173177\"}," +
			"\"Surplus1\":{\"value\":\"SURPLUS1\",\"color\":\"#173177\"}," +
			"\"remark\":{\"value\":\"REMARK\",\"color\":\"#000000\"}";
	
	private static final String TOP_COLOR = "#3299CC";
	
	//private static final String TEMPLATE_ID = "ijRmxSc2e0nHEqA5CIMk4QTcB36F_TWXCmMaOHt5YRw";
	private static final String TEMPLATE_ID = "fIlurqQ3M499ell_xlSJm3StSUXV2VZjzDA12RNkdXU";
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getData() {
		
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public String toJSONString(){
		StringBuilder builder = new StringBuilder();
		builder.append("{\"touser\":\"");
		builder.append(this.getToUserName());		
		builder.append("\",\"template_id\":\"");
		builder.append(TEMPLATE_ID);
		builder.append("\",\"url\":\"");
		builder.append(this.getUrl());
		builder.append("\",\"topcolor\":\"");
		builder.append(TOP_COLOR);
		builder.append("\",\"data\":{");
		builder.append(this.getData());
		builder.append("}}");
		return builder.toString();
	}

}
