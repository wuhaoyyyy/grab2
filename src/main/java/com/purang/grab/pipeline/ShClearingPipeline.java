package com.purang.grab.pipeline;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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


	private static Log taskLog = LogFactory.getLog("grabtask");
	private static int downloadCount=0;
	private static int insertCount=0;
//	private static int downloadThreadCount=1;
	private static ExecutorService executorService=Executors.newFixedThreadPool(10);
//	public int getDownloadThreadCount() {
//		return downloadThreadCount;
//	}
//	public void setDownloadThreadCount(int downloadThreadCount) {
//		this.downloadThreadCount = downloadThreadCount;
//	}

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
			try {
				downloadurl=downloadurl+"DownName="+URLEncoder.encode(descArray[i],"UTF-8")+"&";
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			HashMap<String, Object> single=new HashMap<String, Object>();
			single=result;
			single.put("announceTitle2", descArray[i]);
			single.put("link2", downloadurl);
			setTotalEntityMap(single);
			
			final BondAnnouncement bondAnnouncement=(BondAnnouncement) getEntity(single);
			bondAnnouncement.preInsert();
			final String ftpSaveDir="/bondannounce/"+bondAnnouncement.getAnnounceDate();
			String ftpSaveName=bondAnnouncement.getFt()+".pdf";
			bondAnnouncement.setAttachment("ftp://"+FtpUtils.ftpserver+ftpSaveDir+"/"+ftpSaveName);
			final String durl=downloadurl;
			Callable<String> callable=new Callable<String>() {
				@Override
				public String call() throws Exception {
					downloadFileToFtpGet(durl,ftpSaveDir,bondAnnouncement.getFt()+".pdf");
					taskLog.info(Thread.currentThread().getName()+"文件下载..."+(downloadCount++)+":"+durl);

					BondAnnouncementService service = (BondAnnouncementService) ac.getBean("bondAnnouncementService");
					service.insert(bondAnnouncement);
					taskLog.info(Thread.currentThread().getName()+"数据保存..."+insertCount++);
					return null;
				}
			};
			//Future<String> future=executorService.submit(callable);
			
//			try {
//				future.get();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			} catch (ExecutionException e) {
//				e.printStackTrace();
//			}
			
			//String ftpSavePath="/"+bondAnnouncement.getAnnounceDate()+"/"+bondAnnouncement.getFt()+".pdf";
			
			
			i++;
		}
//		
//		
//		
//		
//		setTotalEntityMap(result);
//		System.out.println(result.toString());
	}
	
}
