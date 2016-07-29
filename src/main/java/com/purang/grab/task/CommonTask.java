package com.purang.grab.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.UrlUtils;

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
		this.processor.setBaseUrl(url);
		Request request=new Request(UrlUtils.canonicalizeUrl(url,url));
		request.putExtra("level", 0);
		if(spider!=null){
			spider.start();
			return;
		}
		spider = Spider.create(processor).addRequest(request).thread(threadCount);
		for(Pipeline pipeline:pipelineList){
			spider.addPipeline(pipeline);
		}
		spider.start();
	}
	@Override
	public void stop(){
		spider.stop();
		//spider.close();
	}
	
	
}
