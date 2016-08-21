package com.purang.grab.task;

import java.util.ArrayList;
import java.util.List;

import com.purang.grab.processor.AbstractProcessor;
import com.purang.grab.processor.Processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class CommonPageProcessor implements PageProcessor{
	private String baseUrl;
    private Site site;
	private List<Processor> processorList=new ArrayList<Processor>();

	public String getBaseUrl() {
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
	}
	public List<Processor> getProcessorList() {
		return processorList;
	}
	public void setProcessorList(List<Processor> processorList) {
		this.processorList = processorList;
	}

	public void process(Page page) {
		int level=(Integer) page.getRequest().getExtra("level");
		Processor processor=processorList.get(level);//这里限定根据level选取对应processor处理 processor里无须判断，但processor里需要加上level传给pipline处理
		((AbstractProcessor)processor).setLevel(level);
		page.putField("level",level);//交给pipeline处理
		processor.process(page);
	}
	
}
