package com.purang.grab.rule;

import java.util.ArrayList;
import java.util.List;

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
	public Object getRuleResult(Html html) {
		Object date=super.getRuleResult(html);
		
		if(date instanceof String){
			String datestr=DateUtils.getString(date.toString(), fromDateFormat,toDateFormat);
			return datestr;
		}
		else if(date instanceof List){
			List<String> dateList=new ArrayList<>();
			for(String s:(ArrayList<String>)date){
				String datestr=DateUtils.getString(s, fromDateFormat,toDateFormat);
				dateList.add(datestr);
			}
			return dateList;
		}
		return null;
	}
	
}
