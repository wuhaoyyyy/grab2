package com.purang.grab.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.purang.grab.request.PagerRequest;
import com.purang.grab.util.StringUtils;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * quartz job执行task
 *
 */
public class CommonTask extends Task{
	
	protected int i = 0;  
	private static Log taskLog = LogFactory.getLog("grabtask");
	@Override
	public void doTask(){
		start();
	}
	
	@Override
	public void start(){
		taskLog.info(this.getTaskName()+"-"+(++i)+"start...");
		spider = Spider.create(pageProcessor).thread(threadCount).setExitWhenComplete(true);
		if(downloader!=null){
			spider.setDownloader(downloader);
		}
		//初始化url列表
		if(urlList!=null){
			for(String url:urlList){
				Request request=new Request();
				request.setUrl(url);
				request.putExtra("level",0);
				spider.addRequest(request);
			}			
		}
		//初始化request列表
		if(requestList!=null){
			for(Request request:requestList){
				//初始化request列表中PagerRequest的第一页
				if(request instanceof PagerRequest){
					PagerRequest pagerRequest=(PagerRequest)request;
					
					String firstUrl=pagerRequest.getFirstUrl();
					if(StringUtils.isNotBlank(firstUrl)){
						Request firstRequest=new Request();
						firstRequest.setUrl(firstUrl);
						firstRequest.putExtra("level",0);
						firstRequest.putExtra("defaultValue", pagerRequest.getDefaultValue());
						spider.addRequest(firstRequest);
					}
					pagerRequest.resetStart();
					spider.addRequest(pagerRequest.getNextPager());
				}
			}			
		}
		for(Pipeline pipeline:pipelineList){
			spider.addPipeline(pipeline);
		}
		spider.start();
	}
	@Override
	public void stop(){
		spider.stop();
	}
	@Override
	public void close(){
		spider.close();
	}
	
	
}
