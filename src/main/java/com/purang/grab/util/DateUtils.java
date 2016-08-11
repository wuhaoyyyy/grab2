package com.purang.grab.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import us.codecraft.webmagic.selector.Html;

public class DateUtils {
	
	/*
	 * 传字符串，根据格式返回str
	 */
	public static String getString(String datestr,String fromfmt, String tofmt) {	
		try {
			if(!StringUtils.isNotBlank(datestr)) return "";
			if(fromfmt.equals("chinese")) return getStringFromChStr(datestr,tofmt);
			Date date=new SimpleDateFormat(fromfmt).parse(datestr);
			return (new SimpleDateFormat(tofmt)).format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/*
	 * 传日期，根据格式返回str 
	 */
	public static String getString(Date date, String fmt) {
		try {
			return (new SimpleDateFormat(fmt)).format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/*
	 * 传汉字，返回yyyyMMdd
	 */
	public static String getStringFromChStr(String s){
		String year=s.substring(0, 4);
		String month=s.substring(s.indexOf("年")+1, s.indexOf("月"));
		String day=s.substring(s.indexOf("月")+1, s.indexOf("日"));

		int m=Integer.parseInt(month);
		int d=Integer.parseInt(day);
		if(m<10) month="0"+month;
		if(d<10) day="0"+day;
		return year+month+day;
	}
	
	/*
	 * 传汉字，根据格式返回str
	 */
	public static String getStringFromChStr(String s,String fmt){
		String year=s.substring(0, 4);
		String month=s.substring(s.indexOf("年")+1, s.indexOf("月"));
		String day=s.substring(s.indexOf("月")+1, s.indexOf("日"));

		int y=Integer.parseInt(year);
		int m=Integer.parseInt(month)-1;
		int d=Integer.parseInt(day);
		Calendar calendar=Calendar.getInstance();
		calendar.set(y,m, d);
		Date date=calendar.getTime();
		return (new SimpleDateFormat(fmt)).format(date);
	}
	
	public static String getCurrentDate(String format){
		return getString(new Date(),format);
	}
	
	/*
	 * 传object string或list
	 */
	public static String a(Object date) {
		return null;
	}
}
