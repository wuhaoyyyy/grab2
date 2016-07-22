package com.purang.grab.rule;

import java.util.Date;

import us.codecraft.webmagic.selector.Html;

import com.purang.grab.util.DateUtils;

public class DateExitRule extends ExitRule {
	
	private String dateFormat;
	
	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	@Override
	public Boolean getExit(Html html) {
		Boolean exitResult=true;
		Date date=new Date();
		String datestr=DateUtils.getString(date, dateFormat);
		String datetext=this.getRuleResult(html);
		if(datetext!=null&&!datetext.equals("")){
			if(datestr.equals(datetext)){
				exitResult=false;
			}
			else{
				exitResult=true;
			}
		}
		return exitResult;
	}
}
