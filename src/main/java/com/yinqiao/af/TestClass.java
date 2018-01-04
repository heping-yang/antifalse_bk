package com.yinqiao.af;

import org.junit.Test;

import com.yinqiao.af.utils.SmsSendUtil;

public class TestClass {

	@Test
	public void test() {
		String out_trade_no = "Red99952951509709293677318810613";
		String orderId =  out_trade_no.substring(0, out_trade_no.length() - 3);
		System.out.println(out_trade_no);
		System.out.println(orderId);
		System.out.println(orderId.length());
	}
	
	public static void main(String[] args) {
		//System.out.println(String.valueOf((int)((Math.random()*9+1)*100000)));
		System.out.println(SmsSendUtil.SendSms("15296977700", String.valueOf((int)((Math.random()*9+1)*100000))));
	}
}
