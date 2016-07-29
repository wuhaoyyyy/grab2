package com.purang.grab.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class DownloadFileUtils {

	public static void downloadFileToFtpGet(String url,String filename){
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
}
