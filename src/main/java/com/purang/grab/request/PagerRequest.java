package com.purang.grab.request;


public class PagerRequest extends CommonPager {

	private static final long serialVersionUID = 1L;
	private String firstUrl;
	private String startUrl;
	private int tolerance=1;
	private int start;
	private int init;

	public String getStartUrl() {
		return startUrl;
	}

	public void setStartUrl(String startUrl) {
		this.startUrl = startUrl;
	}

	public String getFirstUrl() {
		return firstUrl;
	}

	public void setFirstUrl(String firstUrl) {
		this.firstUrl = firstUrl;
	}

	public int getTolerance() {
		return tolerance;
	}

	public void setTolerance(int tolerance) {
		this.tolerance = tolerance;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
		init();
	}

	public PagerRequest getNextPager(){
		this.setUrl(getNextUrl());
		this.putExtra("level",0);
		this.putExtra("defaultValue", this.getDefaultValue());//已经加过,为什么要再加一次
		this.putExtra("result", null);
		start=start+tolerance;
		return this;
	}
	
	public String getNextUrl(){
		String url=startUrl.replace("[*]", String.valueOf(start));
		return url;
	}
	
	/*
	 * task定时任务每次重新调用时不会初始化request，故要手动调用执行从新从第一页调用，否者会继续从上次运行完的页数开始。
	 */
	public void init(){
		this.init=this.start;
	}
	public void resetStart(){
		this.start=this.init;
	}
}
