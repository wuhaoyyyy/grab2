package com.purang.grab.rule;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.selector.Html;

import com.purang.grab.util.CommonUtils;

/*
 * 根据总页数 构造所有url
 */
public class UrlPagerRule extends PagerRule {

	private String url;
	private String startField;
	private String pageSizeField;
	private String pageSize;
	private String start;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getStartField() {
		return startField;
	}
	public void setStartField(String startField) {
		this.startField = startField;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	
	public String getPageSizeField() {
		return pageSizeField;
	}
	public void setPageSizeField(String pageSizeField) {
		this.pageSizeField = pageSizeField;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	@Override
	public String getNextPage(Html html){
		this.start=String.valueOf(Integer.parseInt(start)+Integer.parseInt(pageSize));
		return url+"&"+startField+"="+start+"&"+pageSizeField+"="+pageSize;
	}
	@Override
	public List<String> getNextPageList(Html html){
		List<String> r=new ArrayList<>();
		return r;
	}
	
	
}
