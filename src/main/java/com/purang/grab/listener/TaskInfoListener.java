package com.purang.grab.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.purang.grab.util.TaskInfoUtils;

public class TaskInfoListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		TaskInfoUtils.initAllTask();
	}

}
