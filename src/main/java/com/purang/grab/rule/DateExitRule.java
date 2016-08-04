package com.purang.grab.rule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import us.codecraft.webmagic.selector.Html;

import com.purang.grab.util.DateUtils;

public class DateExitRule extends ExitRule {
	
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
	public Boolean getExit(Html html) {
		Boolean exitResult=true;
		Date date=new Date();
		String todaystr=DateUtils.getString(date, toDateFormat);
		Object dateobj=this.getRuleResult(html);
		if(dateobj instanceof String){
			String datestr=DateUtils.getString(dateobj.toString(), fromDateFormat,toDateFormat);
			if(datestr.equals(todaystr)){
				exitResult=false;
			}
			else{
				exitResult=true;
			}
		}
		else if(dateobj instanceof List){
			for(String s:(ArrayList<String>)dateobj){
				String datestr=DateUtils.getString(s, fromDateFormat,toDateFormat);
				if(datestr.equals(todaystr)){
					exitResult=false;
				}
				else{
					exitResult=true;
					break;
				}
			}
		}
		return exitResult;
	}
}
