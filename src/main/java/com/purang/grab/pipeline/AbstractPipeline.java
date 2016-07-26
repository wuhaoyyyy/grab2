package com.purang.grab.pipeline;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.purang.grab.common.persistence.BaseEntity;
import com.purang.grab.util.FtpUtils;
import com.purang.grab.util.Reflections;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public abstract class AbstractPipeline implements Pipeline {

	String className;
	public HashMap<String, String> defaultValue;
	
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public HashMap<String, String> getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(HashMap<String, String> defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public void process(ResultItems resultItems, Task task) {
	
	}
	
	public void setTotalEntityMap(HashMap tomap){
		for(String key:defaultValue.keySet()){
			tomap.put(key, defaultValue.get(key));
		}
	}
	
	public BaseEntity getEntity(HashMap entityMap){
		try {
			Class clz=Class.forName(className);
			BaseEntity instance=(BaseEntity) clz.newInstance();
			
			Field[] fields=clz.getDeclaredFields();
			String[] fieldnames=new String[fields.length];
			int i=0;
			for(Field field:fields){
				fieldnames[i]=field.getName();
				i++;
			}
			for(String fieldName:fieldnames){
				if(entityMap.containsKey(fieldName)){
					Reflections.invokeSetter(instance, fieldName, entityMap.get(fieldName));
				}
			}
			return instance;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	
	public void downloadFileToFtpGet(String url,String filename){
		try {
			HttpClient client = new DefaultHttpClient();  
			HttpGet httpget = new HttpGet(url);  
			HttpResponse response = client.execute(httpget);  
			HttpEntity entity = response.getEntity();  
			InputStream is = entity.getContent();
			
			FtpUtils.upload(is, filename);
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void downloadFileToFtpPost(String url,String filename){
		
//		HttpClient client = new DefaultHttpClient();  
//		HttpPost httppost=new HttpPost("http://www.shclearing.com/wcm/shch/pages/client/download/download.jsp");
//		List<NameValuePair> nvps = new ArrayList <NameValuePair>(); 
//		String postFileName=fileArray[i].substring(fileArray[i].indexOf("/")+1);
//		nvps.add(new BasicNameValuePair("FileName", postFileName)); 
//		nvps.add(new BasicNameValuePair("DownName", descArray[i]));  
//		try {
//			httppost.setEntity(new UrlEncodedFormEntity(nvps)); 
//			HttpResponse response = client.execute(httppost);  
//			HttpEntity entity = response.getEntity();  
//			InputStream is = entity.getContent();
//			
//			File f=new File("F:\\grabfiles\\"+descArray[i]);
//			OutputStream os=new FileOutputStream(f);
//			
//			byte[] b=new byte[1024];
//			while((is.read(b))!=-1){
//				os.write(b);
//			}
//			is.close();
//			os.close();
//			
//			//FtpUtils.upload(is, descArray[i]);
//			
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
		

	}
}
