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

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

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
	public static String getSelectorResult(Page page,String rule,String type){
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
		return text;
		
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
		return cutEmpty(result);

	}
	

	public static String getSelectorResult(Page page,String rule,String type,String cutPrefix,String cutPostfix){
		String text=null;
		switch(type){
			case "css":
				text=page.getHtml().css(rule).get();
				break;
			case "xpath":
				text=page.getHtml().xpath(rule).get();
				break;
			case "xjson":
				text=page.getJson().jsonPath(rule).get();
				break;
		}
		if(text==null) return null;
		return StringUtils.getCutString(text.trim(), cutPrefix, cutPostfix);
		
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
			case "xpath2":
				Html html=new Html(page.getRawText());//有些href为js函数用webmagic转换无效
				result=html.xpath(rule).all();
				break;
			case "xjson":
				result=page.getJson().jsonPath(rule).all();
				break;
		}
		result=cutEmpty(result);
		if(result!=null&&result.size()>0){
			List<String> resultNew=new ArrayList<String>();
			for(String t:result){
				resultNew.add(StringUtils.getCutString(t, cutPrefix, cutPostfix));
			}
			return resultNew;
		}
		return null;

	}
	
	public static String getSelectorLinkResult(Page page,String rule,String type){
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
		return text;
		
	}
	public static List<String> getSelectorLinkListResult(Page page,String rule,String type){
		switch(type){
			case "css":
				return page.getHtml().css(rule).links().all();
			case "xpath":
				Html html=new Html(page.getRawText());
				List<String> hrefList=html.xpath(rule+"/@href").all();
				List<String> resultList=new ArrayList<String>();
				if(hrefList.size()>0&&!(hrefList.get(0).substring(0, 2).equals("./")))
					return page.getHtml().xpath(rule).links().all();
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
				return cutEmpty(resultList);
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
		
		Map<String,List<String>> mapNew=new HashMap<String, List<String>>();
		for(String key:map.keySet()){
			Object value=map.get(key);
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
			else if(value instanceof List){
				List<String> l=(List<String>) value;//list少于最大数 补全
				if(l.size()<listsize){
					int differ=listsize-l.size();
					for(int i=0;i<differ;i++){
						l.add("");
					}
				}
				mapNew.put(key, l);
			}
		}
		map.putAll(mapNew);
		
		return listsize;
	}
	/*
	 * h获取map中一行
	 */
	
	public static Map getSingleMap(Map<String, Object> map,int i){
		Map<String,String> singleMap=new HashMap<String, String>();
		for(String key:map.keySet()){
			if(map.get(key) instanceof List){
				List<String> v=(List<String>)map.get(key);
				try{
					if(v.size()<1){
						singleMap.put(key, "");
					}
					else{
						singleMap.put(key, v.get(i));
					}
				}
				catch (Exception e) {
					e.printStackTrace();
					System.out.println(map);
				}
			}
			else if(map.get(key) instanceof String){
				singleMap.put(key, map.get(key).toString());
			}
		}
		return singleMap;
	}
	
	
	public static String getSingleSql(String sql,Map<String, Object> map,Map<String, String> mapValue,int index,String id){
		for(String key:map.keySet()){
			String mapkey="[(map)"+key+"]";
			String currentvalue=null;
			Object value=map.get(key);
			if(value instanceof List){
				currentvalue=((List)map.get(key)).get(index).toString();
				if(((List)map.get(key)).get(index)==null) currentvalue="";
			}
			else if(value instanceof String){
				currentvalue=(String) value;
			}
			
			if(sql.indexOf("["+key+"]")>0){
				if(StringUtils.isNotBlank(currentvalue)){
					sql=sql.replace("["+key+"]", "'"+currentvalue+"'");
				}
				else{
					sql=sql.replace("=["+key+"]", " is null");
					sql=sql.replace("["+key+"]", "null");//select则直接替换=key is null  限定select语句=key无空格  insert则直接替换为null
				}
			}
			else if(sql.indexOf(mapkey)>0){
				sql=sql.replace(mapkey, "'"+mapValue.get(currentvalue)+"'");
			}
		}
		if(StringUtils.isNotBlank(id)){
			if(sql.indexOf("[(auto)id]")>0){
				sql=sql.replace("[(auto)id]", "'"+id+"'");
			}
		}
		for(String autoField:CommonUtils.AUTOFIELD){
			if(sql.indexOf(autoField)>0){
				sql=sql.replace(autoField, CommonUtils.getAutoValue(autoField));
			}
		}
		return sql;
	}
	
	/*
	 * HttpGet下载文件
	 */

	public static String fileDownloadHttpGet(String url, String fileDir ,String fielName){
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet httpget = new HttpGet(url);  
			HttpResponse response = client.execute(httpget);  
			Header[] headers = response.getAllHeaders();
			String fileType="";
        	for(int i=0;i<headers.length;i++) {
        		//Content-Disposition==attachment; filename="fielname"  或者 Content-Disposition==attachment; filename=fielname
        		if(headers[i].getName().equals("Content-Disposition")){
        			String contentDisposition=headers[i].getValue();
        			String desc="filename=";
        			String fileName=contentDisposition.substring(contentDisposition.indexOf(desc)+desc.length(), contentDisposition.length());
        			if(fileName.startsWith("\"")&&fileName.endsWith("\"")){
        				fileName=fileName.substring(1,fileName.length()-1);
        			}
        			fileType=fileName.substring(fileName.lastIndexOf("."), fileName.length());
        		}
        	}
			
			HttpEntity entity = response.getEntity();  
			InputStream is = entity.getContent();
			FtpClientUtils.upload(entity.getContentLength(),url,is, fileDir, fielName+fileType);
			client.close();
			return "ftp://"+FtpUtils.ftpserver+fileDir+fielName+fileType;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<String> cutEmpty(List<String> list){	
		int beginIndex=-1;
		for(int i=0;i<list.size();i++){
			String str=list.get(i);
			if(StringUtils.isBlankCustom(str)){
				boolean allempty=true;
				for(int j=i;j<list.size();j++){
					if(!StringUtils.isBlankCustom(list.get(j))){
						allempty=false;
					}
				}
				if(allempty) {
					beginIndex=i;
					break;
				}
			}
		}
		if(beginIndex>0){
			ArrayList<String> l=new ArrayList<>();
			l.addAll(list.subList(0, beginIndex));
			return l;
		}
		else if(beginIndex==0){
			return null;
		}
		return list;
	}

}
