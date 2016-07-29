package com.purang.grab.task;

import java.util.ArrayList;
import java.util.List;

import com.purang.grab.processor.Processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class CommonProcessor implements PageProcessor{
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

	@Override
	public void process(Page page) {
		int level=(int) page.getRequest().getExtra("level");
		Processor processor=processorList.get(level);
		processor.setLevel(level);
		processor.process(page);
	}
	
}
