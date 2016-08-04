package com.purang.grab.request;

import us.codecraft.webmagic.Request;

public class PagerRequest extends Request {

	private static final long serialVersionUID = 1L;
	private String startUrl;
	private int tolerance=1;
	private int start;
	
	public String getStartUrl() {
		return startUrl;
	}

	public void setStartUrl(String startUrl) {
		this.startUrl = startUrl;
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
	}

	public PagerRequest getNextPager(){
		PagerRequest pagerRequest=new PagerRequest();
		pagerRequest.setStartUrl(this.startUrl);
		start++;
		pagerRequest.setStart(start);
		pagerRequest.setUrl(getNextUrl());
		pagerRequest.putExtra("level",0);
		return pagerRequest;
	}
	
	public String getNextUrl(){
		String url=startUrl.replace("[*]", String.valueOf(start-1));
		return url;
	}
}
