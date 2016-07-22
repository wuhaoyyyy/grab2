package com.purang.grab.rule;

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
	public String getRuleResult(Html html) {
		String date=super.getRuleResult(html);
		String datestr=DateUtils.getString(date, fromDateFormat,toDateFormat);
		return datestr;
	}
	
}
