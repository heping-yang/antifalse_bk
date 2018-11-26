package com.yinqiao.af.utils;

import com.ibm.icu.text.SimpleDateFormat;

public class DataUtil {
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static Integer toInteger(String val, Integer defaultVlaue) {
		if (val == null || "".equals(val.trim())) {
			return defaultVlaue;
		}
		try {
			return Integer.parseInt(val);
		} catch (Exception e) {
			return defaultVlaue;
		}
	}

	public static boolean isEmpty(String val) {
		if (val == null || val.trim().length() == 0) {
			return true;
		}
		return false;
	}
}
