package com.purang.grab.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.BeanFactory;

import com.purang.grab.util.TaskInfoUtils;
import com.purang.grab.task.Task;

public class TaskOperateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Log taskLog = LogFactory.getLog("grabtask");
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String taskgroup=(String) req.getParameter("taskgroup");
		String taskName=(String) req.getParameter("taskname");
		String operation=(String) req.getParameter("operation");
		taskLog.info(taskName+":"+operation);
		
		try {
			if(operation.equals("start")){
				BeanFactory beanFactory=TaskInfoUtils.getTaskFactoryNew(taskName);//每次启动重新实例化Scheduler
				Scheduler scheduler = (Scheduler) beanFactory.getBean("scheduler"); 
				if(!scheduler.isStarted()||scheduler.isInStandbyMode()){
					scheduler.start();
				}
			}
			else if(operation.equals("stop")){
				BeanFactory beanFactory=TaskInfoUtils.getTaskFactory(taskName);
				Scheduler scheduler = (Scheduler) beanFactory.getBean("scheduler"); 
				Task task= (Task)beanFactory.getBean("task"); 
				if(scheduler.isStarted()&&!scheduler.isInStandbyMode()){
					scheduler.shutdown();
					task.stop();
				}
			}
		} catch (SchedulerException e) {
			taskLog.error(e.toString());
			e.printStackTrace();
		}
		resp.sendRedirect("tasklist.jsp?taskgroup="+URLEncoder.encode(taskgroup,"UTF-8"));
	}
	
}
