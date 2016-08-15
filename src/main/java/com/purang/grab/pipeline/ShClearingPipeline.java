package com.purang.grab.pipeline;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import com.purang.grab.util.CommonUtils;
import com.purang.grab.util.FtpUtils;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

public class ShClearingPipeline extends AbstractPipeline {

	private static ExecutorService es=Executors.newFixedThreadPool(10);
	private static Log taskLog = LogFactory.getLog("grabtask");
	@SuppressWarnings("rawtypes")
	@Override
	public void gotoProcess(ResultItems resultItems, Task task, Map<String, Object> result) {
		final List<String> downloadfileurlList=(List<String>) result.get("downloadfileurlList");
		List<String> filedescList=(List<String>) result.get("filedescList");
		result.remove("downloadfileurlList");
		result.remove("filedescList");
		if(downloadfileurlList==null){
			result.put("title2", "");
			result.put("linkurl2", "");
			result.put("ftp", "");
			save.save(result);
			return;
		}
		String pubdate=(String) result.get("pubdate");
		for(int i=0;i<downloadfileurlList.size();i++){
			int j=i;
			List descList=new ArrayList<String>();
			descList.add(filedescList.get(i));
			List linkList=new ArrayList<String>();
			linkList.add(downloadfileurlList.get(i));
			result.put("title2", descList);
			result.put("linkurl2", linkList);
		    String ftpSaveDir="/bondannounce/"+pubdate;
			String ftpSaveName=CommonUtils.getAutoValue("[(auto)id]")+".pdf";
			result.put("ftp", "ftp://"+FtpUtils.ftpserver+ftpSaveDir+"/"+ftpSaveName);
			CommonUtils.fileDownloadHttpGet(downloadfileurlList.get(i),ftpSaveDir,ftpSaveName);
			try{
				save.save(result);
			}
			catch(Exception e){
				taskLog.error(result);
				e.printStackTrace();
			}
		}
	}

}
