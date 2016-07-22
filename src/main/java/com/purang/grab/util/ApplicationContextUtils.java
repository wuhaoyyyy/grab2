package com.purang.grab.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextUtils {
	
	private static final ApplicationContext instance = new ClassPathXmlApplicationContext(new String[]{"spring-context.xml"});

	public ApplicationContextUtils() {
		
	}
	public static synchronized ApplicationContext getInstance(){
		return instance;
	}
	
	

}
