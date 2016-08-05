package com.purang.grab.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import us.codecraft.webmagic.selector.Html;

public class CommonUtils {
	
	public static String configFile="grab.properties";
	
	
	public static String[] AUTOFIELD=new String[]{"[(auto)id]","[(auto)date]"};
	
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
				text=html.css(rule).toString().trim();
				break;
			case "xpath":
				text=html.xpath(rule).toString().trim();
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
				text=html.css(rule).links().toString().trim();
				break;
			case "xpath":
				text=html.xpath(rule).links().toString().trim();
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
	
	/*
	 * 配置文件替换字符串
	 */
	public static String getAutoValue(String autoField){
		switch(autoField){
			case "[(auto)id]":
				return String.valueOf(DistributeUniqueId.getValue());
			case "[(auto)date]":
				return DateUtils.getCurrentDate("yyyyMMddHHmmss");
		}
		return null;
	}
	
	/*
	 * 将map的value全部转为list
	 */
	public static void mapValueToList(HashMap<String, Object> map){
		boolean isList=false;
		int listsize=0;
		for(String key:map.keySet()){
			Object value=map.get(key);
			if(value instanceof List){
				isList=true;
				listsize=((List)value).size();
				break;
			}
		}
		if(isList){
			for(String key:map.keySet()){
				Object value=map.get(key);
				if(value instanceof String){
					List<String> l=new ArrayList<String>();
					for(int i=0;i<listsize;i++){
						l.add(((String) value));
					}
					map.put(key, l);
				}
			}
		}
		else{
			for(String key:map.keySet()){
				Object value=map.get(key);
				List<String> l=new ArrayList<String>();
				l.add(((String) value));
				map.put(key, l);
			}
		}
	}
	
	public static String getSingleSql(String sql,HashMap<String, Object> map,HashMap<String, String> mapValue,int index){
		for(String key:map.keySet()){
			String mapkey="[(map)"+key+"]";
			String currentvalue=((List)map.get(key)).get(index).toString();
			if(sql.indexOf("["+key+"]")>0){
				sql=sql.replace("["+key+"]", "'"+currentvalue+"'");
			}
			else if(sql.indexOf(mapkey)>0){
				sql=sql.replace(mapkey, "'"+mapValue.get(currentvalue)+"'");
			}	
		}
		for(String autoField:CommonUtils.AUTOFIELD){
			if(sql.indexOf(autoField)>0){
				sql=sql.replace(autoField, CommonUtils.getAutoValue(autoField));
			}
		}
		return sql;
	}

}
