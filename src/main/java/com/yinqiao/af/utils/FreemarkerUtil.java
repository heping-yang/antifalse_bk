package com.yinqiao.af.utils;

import java.io.IOException;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public class FreemarkerUtil {
	
	private static FreeMarkerConfigurer freeMarkerConfigurer=null;
	
	public static FreeMarkerConfigurer getFreeMarkerConfigurer() {
		return freeMarkerConfigurer;
	}

	public static void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		FreemarkerUtil.freeMarkerConfigurer = freeMarkerConfigurer;
	}

	public static Template getTemplate(String filename) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException{
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(filename);
		return template;
	}
	
	public static String getProcessString(String filename,Object model) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException{
		Template template = getTemplate(filename);
		return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
	}
}
