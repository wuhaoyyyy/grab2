package com.purang.grab.task;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.UrlUtils;

public class CommonTask extends Task{
	
	@Override
	public void doTask(){
		System.out.println(taskName+"执行中…"+i++);  
		start();
	}
	
	@Override
	public void start(){
		this.processor.setBaseUrl(url);
		Request request=new Request(UrlUtils.canonicalizeUrl(url,url));
		request.putExtra("level", 0);//初始
		Spider spider = Spider.create(processor).addRequest(request).thread(threadCount);
		for(Pipeline pipeline:pipelineList){
			spider.addPipeline(pipeline);
		}
		spider.start();
	}
}
