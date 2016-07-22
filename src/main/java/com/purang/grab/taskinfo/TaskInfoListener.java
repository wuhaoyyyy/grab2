package com.purang.grab.taskinfo;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.purang.grab.task.Task;
import com.purang.grab.util.TaskInfoUtils;

public class TaskInfoListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		List<Task> taskList=TaskInfoUtils.getTaskList();
		
		
		
		
		System.out.println(taskList.toString());
		
		
		
		
	}

}
