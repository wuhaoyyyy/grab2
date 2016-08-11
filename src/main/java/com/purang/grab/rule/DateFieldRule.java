package com.purang.grab.rule;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import com.purang.grab.util.DateUtils;

public class DateFieldRule extends FieldRule {

	
	private String fromDateFormat;
	private String toDateFormat;
	
	public String getFromDateFormat() {
		return fromDateFormat;
	}

	public void setFromDateFormat(String fromDateFormat) {
		this.fromDateFormat = fromDateFormat;
	}

	public String getToDateFormat() {
		return toDateFormat;
	}

	public void setToDateFormat(String toDateFormat) {
		this.toDateFormat = toDateFormat;
	}

	@Override
	public List<String> getRuleResult(Page page) {
		List<String> date=super.getRuleResult(page);

		return getFormatDate(date);
	}
	
	private List<String> getFormatDate(List<String> date){
		List<String> dateList=new ArrayList<>();
		for(String s:(ArrayList<String>)date){
			String datestr=DateUtils.getString(s, fromDateFormat,toDateFormat);
			dateList.add(datestr);
		}
		return dateList;	
	}
	
}
