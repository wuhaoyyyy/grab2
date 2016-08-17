package com.purang.grab.task;

import java.util.ArrayList;
import java.util.List;

import com.purang.grab.downloader.HeaderAddHttpClientDownloader;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.Pipeline;

public abstract class Task {
	public Spider spider;
	protected String taskName=null;//和配置文件同名
	protected String taskDesc=null;
	protected List<String> urlList=null;//抓取单个页面
	protected List<Request> requestList=null;//抓取自定义页面
	protected int timeout=0;
	protected int threadCount=1;
	protected CommonPageProcessor pageProcessor=null;
	protected List<Pipeline> pipelineList=new ArrayList<Pipeline>();	
	protected Boolean autostart=false;
	protected String status;
	protected Downloader downloader;
	
	
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
	public List<String> getUrlList() {
		return urlList;
	}
	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}
	
	public List<Request> getRequestList() {
		return requestList;
	}
	public void setRequestList(List<Request> requestList) {
		this.requestList = requestList;
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
	public CommonPageProcessor getPageProcessor() {
		return pageProcessor;
	}
	public void setPageProcessor(CommonPageProcessor pageProcessor) {
		this.pageProcessor = pageProcessor;
	}
	public List<Pipeline> getPipelineList() {
		return pipelineList;
	}
	public void setPipelineList(List<Pipeline> pipelineList) {
		this.pipelineList = pipelineList;
	}
	
	public Downloader getDownloader() {
		return downloader;
	}
	public void setDownloader(Downloader downloader) {
		this.downloader = downloader;
	}
	public abstract void doTask();
	public abstract void start();
	public abstract void stop();
	public abstract void close();
}
