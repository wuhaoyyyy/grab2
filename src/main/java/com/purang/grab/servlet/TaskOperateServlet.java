package com.purang.grab.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.purang.grab.util.TaskInfoUtils;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;


public class TaskOperateServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(TaskOperateServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String taskName=(String) req.getParameter("taskname");
		String operation=(String) req.getParameter("operation");
		logger.info(taskName+":"+operation);
		
		BeanFactory beanFactory=TaskInfoUtils.getTaskFactory(taskName);
		Scheduler scheduler = (Scheduler) beanFactory.getBean("scheduler"); 
		try {
			if(operation.equals("start")){
				scheduler.start();
			}
			else if(operation.equals("shutdown")){
				scheduler.shutdown();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
}
