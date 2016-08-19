package com.purang.grab.request;

import us.codecraft.webmagic.Page;

import com.purang.grab.rule.FieldRule;


public class PagerRequest extends CommonPager {

	private static final long serialVersionUID = 1L;
	private String firstUrl;
	private String startUrl;
	private int tolerance=1;
	private int start;
	private int max;
	private int init;
	private FieldRule maxPagerRule;

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

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public FieldRule getMaxPagerRule() {
		return maxPagerRule;
	}

	public void setMaxPagerRule(FieldRule maxPagerRule) {
		this.maxPagerRule = maxPagerRule;
	}

	public PagerRequest getNextPager(){
		return getNext();
	}

	public PagerRequest getNextPager(Page page){
		if(isLastPage(page)) return null;
		return getNext();
	}
	
	public PagerRequest getNext(){
		if(max>this.init){
			if(start>max) return null;
		}
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
	
	public Boolean isLastPage(Page page){
		if(maxPagerRule==null) return false;
		Object value=maxPagerRule.getRuleResult(page);
		if(start>Integer.valueOf(value.toString())){
			return true;
		}
		return false;
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
