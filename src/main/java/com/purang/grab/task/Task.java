package com.purang.grab.task;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;

public abstract class Task {
	public Spider spider;
	protected String taskName=null;
	protected String taskDesc=null;
	protected String url=null;
	protected List<String> urlList=null;
	protected int timeout=0;
	protected int threadCount=1;
	protected CommonPageProcessor processor=null;
	protected List<Pipeline> pipelineList=new ArrayList<Pipeline>();	
	protected Boolean autostart=false;
	protected String status;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Boolean getAutostart() {
		return autostart;
	}
	public void setAutostart(Boolean autostart) {
		this.autostart = autostart;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskDesc() {
		return taskDesc;
	}
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public List<String> getUrlList() {
		return urlList;
	}
	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getThreadCount() {
		return threadCount;
	}
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
	public CommonPageProcessor getProcessor() {
		return processor;
	}
	public void setProcessor(CommonPageProcessor processor) {
		this.processor = processor;
	}
	public List<Pipeline> getPipelineList() {
		return pipelineList;
	}
	public void setPipelineList(List<Pipeline> pipelineList) {
		this.pipelineList = pipelineList;
	}
	public void doTask(){};
	public void start(){};
	public void stop(){};
}
