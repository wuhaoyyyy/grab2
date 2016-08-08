package com.purang.grab.pipeline;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.purang.grab.util.CommonUtils;
import com.purang.grab.util.FtpUtils;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

public class ShClearingPipeline extends AbstractPipeline {

	private static Log taskLog = LogFactory.getLog("grabtask");
	@Override
	public void process(ResultItems resultItems, Task task) {
		
		HashMap<String, Object> result=resultItems.get("result");
		if(result==null) return;
		if(!resultItems.get("level").toString().equals("1")) return;
		List<String> downloadfileurlList=(List<String>) result.get("downloadfileurlList");
		List<String> filedescList=(List<String>) result.get("filedescList");
		String pubdate=result.get("pubdate").toString();
		for(int i=0;i<downloadfileurlList.size();i++){
			result.put("title2", filedescList.get(i));
			result.put("linkurl2", downloadfileurlList.get(i));
			//System.out.println(result.get("pubdate"));//与上面打印结果不一致。。。
		    String ftpSaveDir="/bondannounce/"+pubdate;
			String ftpSaveName=CommonUtils.getAutoValue("[(auto)id]")+".pdf";
			result.put("ftp", "ftp://"+FtpUtils.ftpserver+ftpSaveDir+"/"+ftpSaveName);
			
			
			downloadFileToFtpGet(downloadfileurlList.get(i),ftpSaveDir,ftpSaveName);
			//taskLog.info(Thread.currentThread().getName()+"文件下载..."+(downloadCount++)+":"+durl);
			//taskLog.info(Thread.currentThread().getName()+"数据保存..."+insertCount++);
			save.save(result);
		}
	}

	public void downloadFileToFtpGet(String url, String fileDir ,String fielName){
		try {
			HttpClient client = new DefaultHttpClient();  
			HttpGet httpget = new HttpGet(url);  
			HttpResponse response = client.execute(httpget);  
			HttpEntity entity = response.getEntity();  
			InputStream is = entity.getContent();
			
			FtpUtils.upload(is, fileDir, fielName);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
