package com.purang.grab.task;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.UrlUtils;

public class CommonTask extends Task{
	

	protected int i = 0;  
	@Override
	public void doTask(){
//		while(true){
//			System.out.println(taskName+"执行中…"+i++); 
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} 
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
