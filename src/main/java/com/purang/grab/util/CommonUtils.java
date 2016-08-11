package com.purang.grab.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import us.codecraft.webmagic.Page;
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
	public static List<String> getSelectorResult(Page page,String rule,String type){
		List<String> result=null;
		String text=null;
		switch(type){
			case "css":
				text=page.getHtml().css(rule).toString().trim();
				break;
			case "xpath":
				text=page.getHtml().xpath(rule).toString().trim();
				break;
			case "xjson":
				text=page.getJson().jsonPath(rule).get().trim();
				break;
		}
		if(StringUtils.isNotBlank(text)){
			result=new ArrayList<String>();
			result.add(text);
		}
		return result;
		
	}
	
	public static List<String> getSelectorListResult(Page page,String rule,String type){
		List<String> result=null;
		switch(type){
			case "css":
				result=page.getHtml().css(rule).all();
				break;
			case "xpath":
				result=page.getHtml().xpath(rule).all();
				break;
			case "xjson":
				result=page.getJson().jsonPath(rule).all();
				break;
		}
		return result;

	}
	

	public static List<String> getSelectorResult(Page page,String rule,String type,String cutPrefix,String cutPostfix){
		List<String> resultList=null;
		String text=null;
		switch(type){
			case "css":
				text=page.getHtml().css(rule).toString().trim();
				break;
			case "xpath":
				text=page.getHtml().xpath(rule).toString().trim();
				break;
			case "xjson":
				text=page.getJson().jsonPath(rule).get().trim();
				break;
		}
		if(StringUtils.isNotBlank(text)){
			resultList=new ArrayList<String>();
			resultList.add(StringUtils.getCutString(text, cutPrefix, cutPostfix));
		}
		return resultList;
		
	}
	
	public static List<String> getSelectorListResult(Page page,String rule,String type,String cutPrefix,String cutPostfix){
		List<String> result=null;
		switch(type){
			case "css":
				result=page.getHtml().css(rule).all();
				break;
			case "xpath":
				result=page.getHtml().xpath(rule).all();
				break;
			case "xjson":
				result=page.getJson().jsonPath(rule).all();
				break;
		}
		List<String> cutResult=new ArrayList<String>();
		for(String text:result){
			cutResult.add(StringUtils.getCutString(text, cutPrefix, cutPostfix));
		}
		return cutResult;

	}
	
	public static List<String> getSelectorLinkResult(Page page,String rule,String type){
		List<String> resultList=new ArrayList<String>();
		String text=null;
		switch(type){
			case "css":
				text=page.getHtml().css(rule).links().toString().trim();
				break;
			case "xpath":
				Html html=new Html(page.getRawText());
				//对相对路径处理 
				String href=html.xpath(rule+"/@href").get();
				if(href.substring(0, 2).equals("./")){
					String url=page.getRequest().getUrl();
					int lastIndex=url.lastIndexOf("/");
					String prefix=url.substring(0, lastIndex);
					text=prefix+href.substring(1);
				}
				else{
					text=page.getHtml().xpath(rule).links().toString().trim();
				}
				break;
			case "xjson":
				text=page.getJson().jsonPath(rule).links().toString().trim();
				break;
		}
		resultList.add(text);
		return resultList;
		
	}
	public static List<String> getSelectorLinkListResult(Page page,String rule,String type){
		switch(type){
			case "css":
				return page.getHtml().css(rule).links().all();
			case "xpath":
				Html html=new Html(page.getRawText());
				List<String> hrefList=html.xpath(rule+"/@href").all();
				List<String> resultList=new ArrayList<String>();
				for(String href:hrefList){
					if(href.substring(0, 2).equals("./")){
						String url=page.getRequest().getUrl();
						int lastIndex=url.lastIndexOf("/");
//						String endstr=url.substring(lastIndex+1, url.length());
//						if(endstr.indexOf(".")>0)
						String prefix=url.substring(0, lastIndex);
						resultList.add(prefix+href.substring(1));
					}
				}
				return resultList;
			case "xjson":
				return page.getJson().jsonPath(rule).links().all();
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
	public static int mapValueToList(Map<String, Object> map){
		boolean isList=false;
		int listsize=0;
		for(String key:map.keySet()){
			Object value=map.get(key);
			if(value instanceof List){
				isList=true;
				if(((List)value).size()>listsize) listsize=((List)value).size();
			}
		}
		if(isList){
			for(String key:map.keySet()){
				Object value=map.get(key);
				Map<String,List<String>> mapNew=new HashMap<String, List<String>>();
				if(value==null){
					List<String> l=new ArrayList<String>();
					for(int i=0;i<listsize;i++){
						l.add("");
					}
					mapNew.put(key, l);
				}
				if(value instanceof String){
					List<String> l=new ArrayList<String>();
					if(listsize==0) listsize=1;
					for(int i=0;i<listsize;i++){
						l.add(((String) value));
					}
					mapNew.put(key, l);
				}
				map.putAll(mapNew);
			}
		}
		else{
			Map<String,List<String>> mapNew=new HashMap<String, List<String>>();
			for(String key:map.keySet()){
				Object value=map.get(key);
				List<String> l=new ArrayList<String>();
				l.add(((String) value));
				mapNew.put(key, l);
				listsize=1;
			}
			map.putAll(mapNew);
		}
		return listsize;
	}
	/*
	 * h获取map中一行
	 */
	
	public static Map getSingleMap(Map<String, Object> map,int i){
		Map<String,String> singleMap=new HashMap<String, String>();
		for(String key:map.keySet()){
			List<String> v=(List<String>)map.get(key);
			singleMap.put(key, v.get(i));
		}
		return singleMap;
	}
	
	
	public static String getSingleSql(String sql,Map<String, Object> map,Map<String, String> mapValue,int index){
		for(String key:map.keySet()){
			String mapkey="[(map)"+key+"]";
			String currentvalue=((List)map.get(key)).get(index).toString();
			if(sql.indexOf("["+key+"]")>0){
				if(StringUtils.isNotBlank(currentvalue)){
					sql=sql.replace("["+key+"]", "'"+currentvalue+"'");
				}
				else{
					sql=sql.replace("["+key+"]", "null");
				}
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
