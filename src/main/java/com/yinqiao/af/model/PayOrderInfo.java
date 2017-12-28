package com.yinqiao.af.model;

public class PayOrderInfo {
	/**
	 * 订单ID
	 */
	private String orderid;
	
	/**
	 * 支付号码
	 */
	private String pay_phone;
	
	/**
	 * 支付机构
	 */
	private String pay_orga;
	
	/**
	 * 充值号码
	 */
	private String phone;
	
	/**
	 * 业务类型
	 */
	private String order_type;
	
	/**
	 * 渠道ID
	 */
	private String channel_id;
	
	/**
	 * 充值金额
	 */
	private String amount;
	
	/**
	 * 备注
	 */
	private String description;
	
	/**
	 * 充值日期
	 */
	private String pay_date;
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 状态日期
	 */
	private String stauts_date;
	
	/**
	 * 支付网关交易流水号
	 */
	private String trade_no;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getPay_phone() {
		return pay_phone;
	}

	public void setPay_phone(String pay_phone) {
		this.pay_phone = pay_phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPay_date() {
		return pay_date;
	}

	public void setPay_date(String pay_date) {
		this.pay_date = pay_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStauts_date() {
		return stauts_date;
	}

	public void setStauts_date(String stauts_date) {
		this.stauts_date = stauts_date;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getPay_orga() {
		return pay_orga;
	}

	public void setPay_orga(String pay_orga) {
		this.pay_orga = pay_orga;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	@Override
	public String toString() {
		return "PayOrderInfo [orderid=" + orderid + ", pay_phone=" + pay_phone
				+ ", pay_orga=" + pay_orga + ", phone=" + phone
				+ ", order_type=" + order_type + ", channel_id=" + channel_id
				+ ", amount=" + amount + ", description=" + description
				+ ", pay_date=" + pay_date + ", status=" + status
				+ ", stauts_date=" + stauts_date + ", trade_no=" + trade_no
				+ "]";
	}
}
