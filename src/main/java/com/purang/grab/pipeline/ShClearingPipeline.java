package com.purang.grab.pipeline;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.context.ApplicationContext;

import com.purang.grab.entity.BondAnnouncement;
import com.purang.grab.service.BondAnnouncementService;
import com.purang.grab.util.ApplicationContextUtils;
import com.purang.grab.util.DateUtils;
import com.purang.grab.util.FtpUtils;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

public class ShClearingPipeline extends AbstractPipeline{

	@Override
	public void process(ResultItems resultItems, Task task) {
		Object level=resultItems.get("level");
		if(!level.toString().equals("1")) return;
		final ApplicationContext ac = ApplicationContextUtils.getInstance() ;	
		
		
		HashMap<String, Object> result=resultItems.get("result");
		String fileNames=(String) result.get("fileNames");
		String descNames=(String) result.get("descNames");
		String[] fileArray=fileNames.split(";;");
		String[] descArray=descNames.split(";;");
		int i=0;
		for(String file:fileArray){

			
			String downloadurl="http://www.shclearing.com/wcm/shch/pages/client/download/download.jsp?";
			String postFileName=fileArray[i].substring(fileArray[i].indexOf("/")+1);
			downloadurl=downloadurl+"FileName="+postFileName+"&";
			downloadurl=downloadurl+"DownName="+descArray[i]+"&";
			downloadFileToFtpGet(downloadurl,postFileName);
			
			
			i++;
		}
		
		
		
		
		setTotalEntityMap(result);
		System.out.println(result.toString());
	}
	
}
