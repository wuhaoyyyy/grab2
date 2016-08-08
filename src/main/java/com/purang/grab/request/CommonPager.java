package com.purang.grab.request;

import java.util.HashMap;

import us.codecraft.webmagic.Request;

public class CommonPager extends Request {

	private static final long serialVersionUID = 1L;
			
	protected HashMap<String, String> defaultValue;

	public HashMap<String, String> getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(HashMap<String, String> defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	
	
}
