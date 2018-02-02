package com.yinqiao.af.weixin.msg;
/**
 * 
 *  消息基类 公众账号到普通用户
 */
public class BaseResponseMessage {
	//接收方账号 OpenID
	private String ToUserName;
	
	//开发者微信号
	private String FromUserName;
	
	//消息创建时间
	private long CreateTime;
	
	//消息类型（text/music/news）
	private String MsgType;
	
	//位0x0001被标志时，标识刚收到的消息 
	private int FuncFlag;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		this.ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		this.CreateTime = createTime;
	}

	public String getMessageType() {
		return MsgType;
	}

	public void setMessageType(String messageType) {
		this.MsgType = messageType;
	}

	public int getFunFlag() {
		return FuncFlag;
	}

	public void setFunFlag(int funFlag) {
		this.FuncFlag = funFlag;
	}
	
	public String toJSONString(){
		return null;
	}
}