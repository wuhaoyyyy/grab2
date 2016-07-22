package com.purang.grab.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	public static String getString(String datestr,String fromfmt, String tofmt) {
		
		try {
			Date date=new SimpleDateFormat(fromfmt).parse(datestr);
			return (new SimpleDateFormat(tofmt)).format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getString(Date date, String fmt) {
		try {
			return (new SimpleDateFormat(fmt)).format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
