package com.purang.grab.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import us.codecraft.webmagic.selector.Html;

public class CommonUtils {
	
	public static String configFile="grab.properties";
	
	
	public static String getConfig(String key){
		InputStream is=CommonUtils.class.getClassLoader().getResourceAsStream(configFile);
		Properties p=new Properties();
		try {
			p.load(is);
			return p.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String getSelectorResult(Html html,String rule,String type){
		String text=null;
		switch(type){
			case "css":
				text=html.css(rule).toString();
				break;
			case "xpath":
				text=html.xpath(rule).toString();
				break;
		}
		return text;
		
	}
	
	public static List<String> getSelectorListResult(Html html,String rule,String type){
		List<String> result=null;
		switch(type){
			case "css":
				result=html.css(rule).all();
				break;
			case "xpath":
				result=html.xpath(rule).all();
				break;
		}
		return result;

	}
	
	public static String getSelectorLinkResult(Html html,String rule,String type){
		String text=null;
		switch(type){
			case "css":
				text=html.css(rule).links().toString();
				break;
			case "xpath":
				text=html.xpath(rule).links().toString();
				break;
		}
		return text;
		
	}
	public static List<String> getSelectorLinkListResult(Html html,String rule,String type){
		switch(type){
			case "css":
				return html.css(rule).links().all();
			case "xpath":
				return html.xpath(rule).links().all();
		}
		return null;
	}

}
