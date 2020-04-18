package com.yinqiao.af.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonDateValueProcessor implements JsonValueProcessor {
	private String format;

	public JsonDateValueProcessor(String format) {
		this.format = format;
	}

	@Override
	public Object processArrayValue(Object value, JsonConfig config) {
		if (value != null && value instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format((Date) value);
		}
		return null;
	}

	@Override
	public Object processObjectValue(String arg0, Object value, JsonConfig config) {
		return processArrayValue(value, config);
	}

}
